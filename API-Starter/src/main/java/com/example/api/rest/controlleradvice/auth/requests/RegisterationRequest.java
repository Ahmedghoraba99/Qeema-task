package com.example.api.rest.controlleradvice.auth.requests;

public class RegisterationRequest {

    private String name;
    private String email;
    private String password;

    public RegisterationRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
