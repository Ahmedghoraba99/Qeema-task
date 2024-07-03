package com.example.API_Starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Starter.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
