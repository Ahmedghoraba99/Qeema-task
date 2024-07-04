package com.example.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Users;
import com.example.api.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.api.rest.controlleradvice.auth.response.TokenResponse;
import com.example.api.service.AuthService;
import com.example.api.service.JwtService;
import com.example.api.service.UsersService;

;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private UsersService service;
    private AuthService authService;

    @Autowired
    AuthController(@Lazy UsersService service, AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping("register")
    public Users register(@RequestBody Users user) {
        return service.save(user);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest user) {
        TokenResponse res = authService.generateToken(user);
        return ResponseEntity.ok(res);
    }

}
