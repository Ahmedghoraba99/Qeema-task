package com.example.API_Starter.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.Product;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
