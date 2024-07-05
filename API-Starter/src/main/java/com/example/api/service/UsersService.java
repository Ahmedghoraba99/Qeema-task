package com.example.api.service;

import java.util.Optional;

import com.example.api.entity.Users;
import com.example.api.repository.UserRepository;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;

public interface UsersService extends UserRepository {

    Optional<Users> findByEmail(String email);

    RegisterationResponse registerUser(RegisterationRequest registerationRequest);
}
