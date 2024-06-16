package com.example.API_Starter.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Starter.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
