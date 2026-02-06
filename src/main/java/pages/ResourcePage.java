package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ResourcePage {
    private RequestSpecification request;
    private static final String RESOURCE_ENDPOINT = "/unknown";

    public ResourcePage() {
        request = RestAssured.given();
    }

    public Response getResourceById(String resourceId) {
        return RestAssured.given().get(RESOURCE_ENDPOINT + "/" + resourceId);
    }

    public void resetRequest() {
        request = RestAssured.given();
    }
}
