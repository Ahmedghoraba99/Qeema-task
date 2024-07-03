package com.example.API_Starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Starter.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
