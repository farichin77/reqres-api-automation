package tests.users;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResourcePage;
import utils.CsvDataReader;

import static org.hamcrest.Matchers.*;

public class GetUserByIdTest extends BaseTest {

    private ResourcePage resourcePage = new ResourcePage();

    @Test(description = "GET resource by ID - Valid resource")
    public void testGetResourceById() {
        logInfo("Testing GET resource by ID");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testGetResourceById", "Positive");
            
            Response response = resourcePage.getResourceById(testData.userId);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            // Validasi response structure untuk Resource
            response.then()
                    .statusCode(testData.expectedStatus)
                    .body("$", hasKey("data"))
                    .body("$", hasKey("support"))
                    .body("data.id", notNullValue())
                    .body("data.name", notNullValue())
                    .body("data.year", notNullValue())
                    .body("data.color", notNullValue())
                    .body("data.pantone_value", notNullValue());
            
            logInfo("GET resource by ID test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }

    @Test(description = "GET resource by ID - Non-existent resource")
    public void testGetNonExistentResource() {
        logInfo("Testing GET non-existent resource by ID");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testGetNonExistentResource", "Negative");
            
            Response response = resourcePage.getResourceById(testData.userId);
            logResponse(response);

            // Expect 404 for non-existent resource
            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            logInfo("GET non-existent resource test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }
}
