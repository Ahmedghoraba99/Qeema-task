package com.example.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;
import com.example.api.rest.controlleradvice.auth.response.TokenResponse;
import com.example.api.service.AuthService;
import com.example.api.service.JwtService;
import com.example.api.service.UsersService;

;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private UsersService userService;
    private AuthService authService;

    @Autowired
    AuthController(@Lazy UsersService service, AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = service;
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<RegisterationResponse> register(@RequestBody RegisterationRequest request) {
        RegisterationResponse res = userService.registerUser(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest user) {
        TokenResponse res = authService.generateToken(user);
        return ResponseEntity.ok(res);
    }

}
