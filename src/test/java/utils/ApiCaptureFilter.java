package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiCaptureFilter implements Filter {

    private final int maxBodyChars;

    public ApiCaptureFilter() {
        this(8000);
    }

    public ApiCaptureFilter(int maxBodyChars) {
        this.maxBodyChars = maxBodyChars;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        ApiCallContext.ApiCall call = new ApiCallContext.ApiCall();

        try {
            call.method = requestSpec.getMethod();
            call.uri = requestSpec.getURI();
            if (requestSpec.getHeaders() != null) {
                call.requestHeaders = requestSpec.getHeaders().toString();
            }

            Object bodyObj = requestSpec.getBody();
            if (bodyObj != null) {
                call.requestBody = ApiCallContext.truncate(String.valueOf(bodyObj), maxBodyChars);
            }
        } catch (Exception ignored) {
        }

        Response response = ctx.next(requestSpec, responseSpec);

        try {
            call.statusCode = response.getStatusCode();
            if (response.getHeaders() != null) {
                call.responseHeaders = response.getHeaders().toString();
            }
            String responseBody = response.asString();
            call.responseBody = ApiCallContext.truncate(responseBody, maxBodyChars);
        } catch (Exception ignored) {
        }

        ApiCallContext.set(call);
        return response;
    }
}
