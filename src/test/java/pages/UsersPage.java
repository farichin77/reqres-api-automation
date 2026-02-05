package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.UsersResponse;
import models.ErrorResponse;
import models.UserUpdate;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class UsersPage {
    private RequestSpecification request;
    private static final String USERS_ENDPOINT = "/users";

    public UsersPage() {
        request = given();
    }

    public UsersPage setQueryParam(String param, Object value) {
        request.queryParam(param, value);
        return this;
    }

    public Response getUsers() {
        return request.get(USERS_ENDPOINT);
    }

    public UsersResponse getUsersAsObject() {
        Response response = getUsers();
        return response.as(UsersResponse.class);
    }

    public Response getUsersWithPage(int page) {
        return setQueryParam("page", page).getUsers();
    }

    public Response getUsersWithPerPage(int perPage) {
        return setQueryParam("per_page", perPage).getUsers();
    }

    public Response getUsersWithPagination(int page, int perPage) {
        return setQueryParam("page", page)
                .setQueryParam("per_page", perPage)
                .getUsers();
    }

    public Response getUserById(String userId) {
        return given().get(USERS_ENDPOINT + "/" + userId);
    }

    public Response createUser(String name, String email) {
        String requestBody = String.format("{\"name\": \"%s\", \"email\": \"%s\"}", name, email);
        
        Response response = given()
                .contentType(JSON)
                .body(requestBody)
                .post(USERS_ENDPOINT);
        
        // Simpan response JSON ke file jika berhasil
        if (response.getStatusCode() == 201) {
            String jsonResponse = response.asString();
            JsonUtils.saveCreatedUser(jsonResponse);
            logInfo("Created user and saved to JSON file");
        }
        
        return response;
    }

    public Response updateUser(String userId, String name, String email) {
        String requestBody = String.format("{\"name\": \"%s\", \"email\": \"%s\"}", name, email);
        
        Response response = given()
                .contentType(JSON)
                .body(requestBody)
                .put(USERS_ENDPOINT + "/" + userId);
        
        logInfo("Updated user with ID: " + userId);
        return response;
    }

    public UserUpdate updateUserAsObject(String userId, String name, String email) {
        Response response = updateUser(userId, name, email);
        return response.as(UserUpdate.class);
    }

    public Response deleteUser(String userId) {
        Response response = given()
                .delete(USERS_ENDPOINT + "/" + userId);
        
        logInfo("Deleted user with ID: " + userId);
        return response;
    }

    public String getLastCreatedUserId() {
        return JsonUtils.getLastCreatedUserId();
    }

    public ErrorResponse getErrorResponse(Response response) {
        return response.as(ErrorResponse.class);
    }

    public void resetRequest() {
        request = given();
    }

    private void logInfo(String message) {
        System.out.println("[UsersPage] " + message);
    }
}
