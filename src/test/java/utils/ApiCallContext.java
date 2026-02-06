package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ApiCallContext {
    public static final class ApiCall {
        public String method;
        public String uri;
        public String requestHeaders;
        public String requestBody;
        public Integer statusCode;
        public String responseHeaders;
        public String responseBody;
    }

    private static final ThreadLocal<ApiCall> LAST_CALL = new ThreadLocal<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ApiCallContext() {
    }

    public static void clear() {
        LAST_CALL.remove();
    }

    public static void set(ApiCall apiCall) {
        LAST_CALL.set(apiCall);
    }

    public static ApiCall get() {
        return LAST_CALL.get();
    }

    public static String prettyJsonIfPossible(String raw) {
        if (raw == null || raw.isBlank()) {
            return raw;
        }

        String trimmed = raw.trim();
        if (!(trimmed.startsWith("{") || trimmed.startsWith("["))) {
            return raw;
        }

        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(OBJECT_MAPPER.readTree(raw));
        } catch (Exception e) {
            return raw;
        }
    }

    public static String truncate(String s, int maxChars) {
        if (s == null) {
            return null;
        }
        if (maxChars <= 0) {
            return "";
        }
        if (s.length() <= maxChars) {
            return s;
        }
        return s.substring(0, maxChars) + "\n... (truncated, total " + s.length() + " chars)";
    }
}
