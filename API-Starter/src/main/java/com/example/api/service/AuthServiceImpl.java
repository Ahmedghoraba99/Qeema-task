package com.example.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.api.entity.Users;
import com.example.api.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.api.rest.controlleradvice.auth.response.TokenResponse;

@Service
public class AuthServiceImpl implements AuthService {

    private UsersService usersService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(@Lazy UsersService usersService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse generateToken(LoginRequest loginRequest) {
        Optional<Users> tempUser = usersService.findByEmail(loginRequest.getUsername());
        if (tempUser.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            String token = jwtService.generateToken(loginRequest.getUsername());
            return new TokenResponse(
                    token, tempUser.get().getId(),
                    tempUser.get().getName()
            );
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
