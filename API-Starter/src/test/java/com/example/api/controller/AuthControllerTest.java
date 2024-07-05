package com.example.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.BDDMockito.given;

import com.example.api.entity.Users;
import com.example.api.rest.AuthController;
import com.example.api.rest.ProductController;
import com.example.api.rest.controlleradvice.auth.requests.LoginRequest;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;
import com.example.api.rest.controlleradvice.auth.response.TokenResponse;
import com.example.api.service.AuthService;
import com.example.api.service.JwtService;
import com.example.api.service.UsersService;

import org.springframework.http.HttpHeaders;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AuthController.class)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    UsersService usersService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    private WebTestClient webClient;

    private String encodedPassword;
    private Users user;
    private TokenResponse tokenResponse;
    private LoginRequest loginRequest;
    private RegisterationRequest registerationRequest;
    private RegisterationResponse registerationResponse;

    @BeforeEach
    public void setUp() {
        webClient = WebTestClient.bindToController(
                new AuthController(usersService, authService, jwtService, authenticationManager))
                .build();
        encodedPassword = "encodedPassword"; // Mock the encoded password

        user = new Users("testUser", "testUser@gmail.com", "123456789");
        tokenResponse = new TokenResponse("token", 1L, "testUser");
        loginRequest = new LoginRequest("testUser@gmail.com", "123456789");
        registerationRequest = new RegisterationRequest("testUser", "testUser@gmail.com", "123456789");
        registerationResponse = new RegisterationResponse(user, "Successfully registered");

    }

    @Test
    public void contextLoads() {
        assertThat(webClient).isNotNull();
        assertThat(authService).isNotNull();
        assertThat(jwtService).isNotNull();
        assertThat(authenticationManager).isNotNull();
    }

    @Test
    public void testRegister() {
        given(usersService.registerUser(any(RegisterationRequest.class)))
                .willReturn(registerationResponse);
        webClient.post().uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerationRequest)
                .exchange()
                .expectStatus().isOk().expectBody()
                .jsonPath("$.message")
                .isEqualTo("Successfully registered");
        verify(usersService).registerUser(any(RegisterationRequest.class));
    }

    @Test
    public void testLogin() {
        given(authService.generateToken(any(LoginRequest.class))).willReturn(tokenResponse);
        webClient.post().uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk().expectBody()
                .jsonPath("$.token")
                .isEqualTo("token");
        verify(authService).generateToken(any(LoginRequest.class));
    }
}
