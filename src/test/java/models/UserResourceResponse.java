package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResourceResponse {
    private UserResource data;
    private Support support;

    // Constructors
    public UserResourceResponse() {}

    public UserResourceResponse(UserResource data, Support support) {
        this.data = data;
        this.support = support;
    }

    // Getters and Setters
    public UserResource getData() {
        return data;
    }

    public void setData(UserResource data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    // Inner Support class
    public static class Support {
        private String url;
        private String text;

        public Support() {}

        public Support(String url, String text) {
            this.url = url;
            this.text = text;
        }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}
