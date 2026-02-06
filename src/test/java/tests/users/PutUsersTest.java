package tests.users;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UsersPage;
import utils.CsvDataReader;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class PutUsersTest extends BaseTest {

    private UsersPage usersPage = new UsersPage();

    @Test(description = "PUT user - Update existing user")
    public void testUpdateUser() {
        logInfo("Testing PUT update user");
        
        try {
            String userId = "2"; // Use existing user ID
            String updatedName = "John Updated";
            String updatedEmail = "john.updated@example.com";
            
            Response response = usersPage.updateUser(userId, updatedName, updatedEmail);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), 200);
            
            // Validasi response structure untuk updated user
            response.then()
                    .statusCode(200)
                    .body("name", equalTo("John Updated"))
                    .body("email", equalTo("john.updated@example.com"))
                    .body("updatedAt", notNullValue());
            
            logInfo("PUT user test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }

    @Test(description = "PUT user - Update non-existent user")
    public void testUpdateNonExistentUser() {
        logInfo("Testing PUT update non-existent user");
        
        try {
            // Get test data from CSV
            CsvDataReader.TestData testData = CsvDataReader.getTestData("testUpdateNonExistentUser", "Negative");
            
            Response response = usersPage.updateUser(testData.userId, testData.name, testData.email);
            logResponse(response);

            Assert.assertEquals(response.getStatusCode(), testData.expectedStatus);
            
            logInfo("PUT non-existent user test passed successfully");
        } catch (Exception e) {
            logInfo("Connection error: " + e.getMessage());
            Assert.fail("Test failed due to connection error: " + e.getMessage());
        }
    }


}
