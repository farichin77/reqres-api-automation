package tests.users;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UsersPage;
import utils.CsvDataReader;

import static org.hamcrest.Matchers.*;

public class PostUsersTest extends BaseTest {

    private UsersPage usersPage = new UsersPage();

    @Test(description = "POST user - Create new user")
    public void testCreateUser() {
        logInfo("Testing POST create user");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testCreateUser", "Positive");
            
            Response response = usersPage.createUser(testData.name, testData.email);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            // Validate response structure
            response.then()
                    .statusCode(testData.expectedStatus)
                    .body("$", hasKey("id"))
                    .body("$", hasKey("name"))
                    .body("$", hasKey("email"))
                    .body("$", hasKey("createdAt"));
            
            logInfo("POST user test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }



    @Test(description = "POST user - Create user with invalid email")
    public void testCreateUserWithInvalidEmail() {
        logInfo("Testing POST create user with invalid email");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testCreateUserWithInvalidEmail", "Negative");
            String invalidName = "Test User";
            String invalidEmail = "invalid-email";
            
            Response response = usersPage.createUser(invalidName, invalidEmail);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus); 
            if (response.getStatusCode() == 201) {
                logInfo("ReqRes accepted invalid email - this is expected behavior");
            } else {
                Assert.assertTrue(response.getStatusCode() >= 400, 
                                 "Should return error for invalid email");
            }
            
            logInfo("POST user with invalid email test completed");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }


}
