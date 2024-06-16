package com.example.API_Starter.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public TokenResponse(String token, long userId2) {
        this.token = token;
        this.userId = userId2;
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
}

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private UsersService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public Users register(@RequestBody Users user) {
        return service.add(user);
    }

    @PostMapping("login")
    public TokenResponse login(@RequestBody Users user) {
        Optional<Users> tempUser = service.findByEmail(user.getUsername());
        long userId = tempUser.get().getId();
        System.out.println("" + user.getEmail() + " " + user.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            jwtService.generateToken(user.getUsername());
            // return token as json
            return new TokenResponse(jwtService.generateToken(user.getUsername()), userId);

        } else {
            throw new RuntimeException("Username or Password is incorrect");
        }

    }
}
