package com.example.api.service;

import java.util.List;

import com.example.api.entity.Order;
import com.example.api.entity.OrderProduct;
import com.example.api.entity.Product;
import com.example.api.entity.Users;
import com.example.api.repository.OrderRepository;

public interface OrderService extends OrderRepository {

    Order initializeOrder(Users user);

    Product findProductById(long productId);

    Order createOrder(long userId, List<OrderItemRequest> orderItemRequests);

    OrderProduct createOrderProduct(Order order, Product product, int quantity);

    void updateProductQuantity(Product product, int quantity);

    void calculateTotalPrice(Order order);
}
