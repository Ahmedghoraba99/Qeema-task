package com.example.API_Starter.service;

import java.util.Optional;

import com.example.API_Starter.entity.Users;

public interface UsersService extends BasicCrudService<Users> {

    Optional<Users> findByEmail(String email);

}
