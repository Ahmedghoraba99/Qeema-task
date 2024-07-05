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
}
