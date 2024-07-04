package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
