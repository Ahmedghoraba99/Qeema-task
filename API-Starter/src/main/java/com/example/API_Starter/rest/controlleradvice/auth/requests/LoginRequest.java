package com.example.API_Starter.rest.controlleradvice.auth.requests;

public class LoginRequest {

    private String email;
    private String password;

    LoginRequest(String usename, String password) {
        this.email = usename;
        this.password = password;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
