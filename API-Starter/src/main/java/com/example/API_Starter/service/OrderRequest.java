package com.example.API_Starter.service;

import java.util.List;

public class OrderRequest {

    private int userId;
    private List<OrderItemRequest> orderItems;

    // Constructors, getters, and setters
    public OrderRequest() {
    }

    public OrderRequest(int userId, List<OrderItemRequest> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
