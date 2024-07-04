package com.example.api.service;

import java.util.List;

import com.example.api.entity.Order;
import com.example.api.repository.OrderRepository;

public interface OrderService extends OrderRepository {

    Order createOrder(long userId, List<OrderItemRequest> orderItemRequests);

}
