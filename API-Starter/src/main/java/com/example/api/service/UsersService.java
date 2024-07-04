package com.example.api.service;

import java.util.Optional;

import com.example.api.entity.Users;
import com.example.api.repository.UserRepository;

public interface UsersService extends UserRepository {

    Optional<Users> findByEmail(String email);

}
