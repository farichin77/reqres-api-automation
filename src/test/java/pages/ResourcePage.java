package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.UserResourceResponse;
import models.ErrorResponse;

import static io.restassured.RestAssured.given;

public class ResourcePage {
    private RequestSpecification request;
    private static final String RESOURCE_ENDPOINT = "/unknown";

    public ResourcePage() {
        request = given();
    }

    public Response getResourceById(String resourceId) {
        return given().get(RESOURCE_ENDPOINT + "/" + resourceId);
    }

    public UserResourceResponse getResourceAsObject(String resourceId) {
        Response response = getResourceById(resourceId);
        return response.as(UserResourceResponse.class);
    }

    public ErrorResponse getErrorResponse(Response response) {
        return response.as(ErrorResponse.class);
    }

    public void resetRequest() {
        request = given();
    }
}
