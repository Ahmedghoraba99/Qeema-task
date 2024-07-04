package com.example.api.rest.controlleradvice.auth.response;

public class TokenResponse {

    private String token;
    private long userId;
    private String name;

    // No-argument constructor
    public TokenResponse() {
    }

    // Constructor with arguments
    public TokenResponse(String token, long userId, String name) {
        this.token = token;
        this.userId = userId;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
