package com.example.API_Starter.service;

import java.util.List;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.repository.OrderRepository;

public interface OrderService extends OrderRepository {

    Order createOrder(long userId, List<OrderItemRequest> orderItemRequests);

}
