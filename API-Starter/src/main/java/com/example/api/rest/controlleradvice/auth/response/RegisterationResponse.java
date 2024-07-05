package com.example.api.rest.controlleradvice.auth.response;

import com.example.api.entity.Users;

public class RegisterationResponse {

    private String name;
    private String email;
    private String message;

    public RegisterationResponse(Users user, String message) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.message = message;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    // Setters (if needed)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
