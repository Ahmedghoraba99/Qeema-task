package com.example.api.rest.controlleradvice.order.request;

import java.util.List;

import com.example.api.rest.controlleradvice.orderitem.OrderItemRequest;

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
