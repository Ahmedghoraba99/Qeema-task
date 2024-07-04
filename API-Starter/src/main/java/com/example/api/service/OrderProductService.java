package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.repository.OrderProductRepository;

public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

}
