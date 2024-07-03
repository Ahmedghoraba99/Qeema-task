package com.example.API_Starter.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Starter.entity.Users;
import com.example.API_Starter.service.JwtService;
import com.example.API_Starter.service.UsersService;

class TokenResponse {

    private String token;
    private long userId;
    private String name;

    public TokenResponse(String token, long userId2, String name) {
        this.token = token;
        this.userId = userId2;
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

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private UsersService service;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    AuthController(@Lazy UsersService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("register")
    public Users register(@RequestBody Users user) {
        return service.save(user);
    }

    @PostMapping("login")
    public TokenResponse login(@RequestBody Users user) {
        Optional<Users> tempUser = service.findByEmail(user.getUsername());
        long userId = tempUser.get().getId();
        String name = tempUser.get().getUsername();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            jwtService.generateToken(user.getUsername());
            // return token as json
            return new TokenResponse(jwtService.generateToken(user.getUsername()), userId, name);

        } else {
            throw new RuntimeException("Username or Password is incorrect");
        }

    }
}
