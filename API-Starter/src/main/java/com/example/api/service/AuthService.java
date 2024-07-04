package com.example.api.service;

import com.example.api.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.api.rest.controlleradvice.auth.response.TokenResponse;

public interface AuthService {

    TokenResponse generateToken(LoginRequest loginRequest);
}
