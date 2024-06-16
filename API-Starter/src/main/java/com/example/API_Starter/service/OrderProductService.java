package com.example.API_Starter.service;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

}
