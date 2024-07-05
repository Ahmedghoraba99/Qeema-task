package com.example.api.rest.controlleradvice.order.response;

import java.util.List;

import com.example.api.entity.Order;
import com.example.api.entity.OrderProduct;

public class OrderResponse {

    private int id;
    private double totalPrice;
    private Long userId;
    private List<OrderProduct> orderProducts;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.userId = order.getUser().getId();
        this.orderProducts = order.getOrderProducts();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
