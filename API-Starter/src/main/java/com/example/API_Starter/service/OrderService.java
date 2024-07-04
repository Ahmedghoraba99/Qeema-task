package com.example.API_Starter.service;

import java.util.List;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.OrderProduct;
import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;
import com.example.API_Starter.repository.OrderRepository;

public interface OrderService extends OrderRepository {

    Order initializeOrder(Users user);

    Product findProductById(long productId);

    Order createOrder(long userId, List<OrderItemRequest> orderItemRequests);

    OrderProduct createOrderProduct(Order order, Product product, int quantity);

    void updateProductQuantity(Product product, int quantity);

    void calculateTotalPrice(Order order);
}
