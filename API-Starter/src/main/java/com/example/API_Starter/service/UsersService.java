package com.example.API_Starter.service;

import java.util.Optional;

import com.example.API_Starter.entity.Users;
import com.example.API_Starter.repository.UserRepository;

public interface UsersService extends UserRepository {

    Optional<Users> findByEmail(String email);

}
