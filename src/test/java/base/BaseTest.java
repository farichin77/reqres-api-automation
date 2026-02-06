

package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ApiCallContext;
import utils.ApiCaptureFilter;
import utils.ConfigReader;
import utils.ExtentManager;

import java.lang.reflect.Method;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void initReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeClass
    public void setUp() {
        // Prioritize system properties (from GitHub Actions), then config file, then default
        String baseUri = System.getProperty("baseURI", 
                        ConfigReader.getProperty("baseUrl", 
                        "https://reqres.in/api"));
        RestAssured.baseURI = baseUri;
        System.out.println("[BaseTest] Base URI configured: " + baseUri);
    }

    @BeforeMethod
    public void startTest(Method method) {
        RestAssured.requestSpecification = null;

        String apiKey = System.getProperty("apiKey", 
                          ConfigReader.getProperty("apiKey", 
                          "reqres_abdc43c872674a8ebf125ac633b65ce5"));

        RestAssured.requestSpecification = given()
                .header("x-api-key", apiKey)
                .contentType("application/json")
                .accept("application/json");
        
        ApiCallContext.clear();
        RestAssured.filters(new ApiCaptureFilter());

        String testName = method.getName();

        // ambil package terakhir sebagai kategori (misal: auth, sportCategory, sportActivity)
        String[] packageParts = method.getDeclaringClass().getPackage().getName().split("\\.");
        String category = packageParts[packageParts.length - 1];

        test = extent.createTest(testName).assignCategory(category);
    }

    protected void logInfo(String message) {
        test.info(message);
    }

    protected void logRequest(String endpoint, Object body) {
        test.info("Endpoint: " + endpoint);
        if (body != null) {
            test.info("Request Body:<br><pre>" + body.toString() + "</pre>");
        }
    }

    protected void logResponse(Response response) {
        test.info("Status Code: " + response.getStatusCode());
        test.info("Response:<br><pre>" + response.asString() + "</pre>");
    }

    @AfterMethod
    public void captureResult(ITestResult result) {

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.pass("Test passed");
                break;

            case ITestResult.FAILURE:
                test.fail(result.getThrowable());

                ApiCallContext.ApiCall lastCall = ApiCallContext.get();
                if (lastCall != null) {
                    StringBuilder requestBlock = new StringBuilder();
                    if (lastCall.method != null || lastCall.uri != null) {
                        requestBlock.append(String.valueOf(lastCall.method))
                                .append(" ")
                                .append(String.valueOf(lastCall.uri))
                                .append("\n\n");
                    }
                    if (lastCall.requestHeaders != null && !lastCall.requestHeaders.isBlank()) {
                        requestBlock.append("Headers:\n")
                                .append(lastCall.requestHeaders)
                                .append("\n\n");
                    }
                    if (lastCall.requestBody != null && !lastCall.requestBody.isBlank()) {
                        requestBlock.append("Body:\n")
                                .append(ApiCallContext.prettyJsonIfPossible(lastCall.requestBody))
                                .append("\n");
                    }

                    StringBuilder responseBlock = new StringBuilder();
                    if (lastCall.statusCode != null) {
                        responseBlock.append("Status Code: ")
                                .append(lastCall.statusCode)
                                .append("\n\n");
                    }
                    if (lastCall.responseHeaders != null && !lastCall.responseHeaders.isBlank()) {
                        responseBlock.append("Headers:\n")
                                .append(lastCall.responseHeaders)
                                .append("\n\n");
                    }
                    if (lastCall.responseBody != null && !lastCall.responseBody.isBlank()) {
                        responseBlock.append("Body:\n")
                                .append(ApiCallContext.prettyJsonIfPossible(lastCall.responseBody))
                                .append("\n");
                    }

                    if (!requestBlock.isEmpty()) {
                        test.info("Request:<br><pre>" + requestBlock + "</pre>");
                    }
                    if (!responseBlock.isEmpty()) {
                        test.info("Response:<br><pre>" + responseBlock + "</pre>");
                    }
                } else {
                    test.info("No API call captured for this test.");
                }
                break;

            case ITestResult.SKIP:
                if (result.getThrowable() != null) {
                    test.skip(result.getThrowable());
                } else {
                    test.skip("Test skipped");
                }
                break;
        }
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }
}
