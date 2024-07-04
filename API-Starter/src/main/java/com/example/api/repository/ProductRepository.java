package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
