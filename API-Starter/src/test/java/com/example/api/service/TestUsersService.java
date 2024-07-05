package com.example.api.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.api.entity.Users;
import com.example.api.repository.UserRepository;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;

@ExtendWith(MockitoExtension.class)
public class TestUsersService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users user;
    private String encodedPassword;

    @BeforeEach
    public void setUp() {
        initMocks(this);

        encodedPassword = "encodedPassword";
        user = new Users("test", "test@test.com", encodedPassword);
    }

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Optional<Users> foundUser = usersService.findByEmail("test@test.com");
        assertNotNull(foundUser);
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testRegisterUser() {
        RegisterationRequest request = new RegisterationRequest("test", "knjmkl@test.com", "password");
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        RegisterationResponse response = usersService.registerUser(request);
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals("User registered successfully", response.getMessage());
    }
}
