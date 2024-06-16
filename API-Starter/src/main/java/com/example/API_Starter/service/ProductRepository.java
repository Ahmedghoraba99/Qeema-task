package com.example.API_Starter.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Starter.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
