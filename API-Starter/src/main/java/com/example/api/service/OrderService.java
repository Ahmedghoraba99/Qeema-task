package com.example.api.service;

import java.util.List;

import com.example.api.entity.Order;
import com.example.api.repository.OrderRepository;
import com.example.api.rest.controlleradvice.order.response.OrderResponse;
import com.example.api.rest.controlleradvice.orderitem.OrderItemRequest;

public interface OrderService extends OrderRepository {

    Order createOrder(long userId, List<OrderItemRequest> orderItemRequest);

    List<OrderResponse> ordersToResponses();

}
