package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
