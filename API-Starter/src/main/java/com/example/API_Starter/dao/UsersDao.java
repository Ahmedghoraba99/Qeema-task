package com.example.API_Starter.dao;

import com.example.API_Starter.entity.Users;

public interface UsersDao extends BasicCrudDao<Users> {

    // find by name
    Users findByName(String name);

    Users findByEmail(String email);
}
