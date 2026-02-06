package tests.users;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UsersPage;
import utils.CsvDataReader;

import static org.hamcrest.Matchers.*;

public class GetUsersTest extends BaseTest {

    private UsersPage usersPage = new UsersPage();

    @Test(description = "GET users - Basic functionality test")
    public void testGetUsers() {
        logInfo("Testing GET users basic functionality");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testGetUsers", "Positive");
            
            Response response = usersPage.getUsers();
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            // Validasi response structure untuk ReqRes
            response.then()
                    .statusCode(testData.expectedStatus)
                    .body("$", hasKey("page"))
                    .body("$", hasKey("per_page"))
                    .body("$", hasKey("total"))
                    .body("$", hasKey("total_pages"))
                    .body("$", hasKey("data"))
                    .body("$", hasKey("support"))
                    .body("data.email", everyItem(containsString("@")));
            
            logInfo("GET users test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }

    @Test(description = "GET user - Get non-existent user")
    public void testGetNonExistentUser() {
        logInfo("Testing GET non-existent user");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testGetNonExistentUser", "Negative");
            
            Response response = usersPage.getUserById(testData.userId);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            logInfo("GET non-existent user test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }




}
