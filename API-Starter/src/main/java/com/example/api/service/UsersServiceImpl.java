package com.example.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.entity.UserPrincipal;
import com.example.api.entity.Users;
import com.example.api.repository.UserRepository;
import com.example.api.rest.controlleradvice.UsersResponses.exceptions.UserNotFoundException;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;

@Service
public class UsersServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Users registerationRequestToUser(RegisterationRequest registerationRequest) {
        return new Users(
                registerationRequest.getName(),
                registerationRequest.getEmail(),
                encryptPassword(registerationRequest.getPassword())
        );
    }

    public RegisterationResponse registerUser(RegisterationRequest registerationRequest) {
        Users user = registerationRequestToUser(registerationRequest);
        userRepository.save(user);
        return new RegisterationResponse(user, "User registered successfully");
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<Users> findByEmail(String email) {
        Optional<Users> temp = this.userRepository.findByEmail(email);
        if (!temp.isPresent()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return temp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User 404"));
        return new UserPrincipal(user);
    }
}
