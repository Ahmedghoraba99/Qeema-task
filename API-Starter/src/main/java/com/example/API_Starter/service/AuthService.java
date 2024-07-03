package com.example.API_Starter.service;

import com.example.API_Starter.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.API_Starter.rest.controlleradvice.auth.response.TokenResponse;

public interface AuthService {

    TokenResponse generateToken(LoginRequest loginRequest);
}
