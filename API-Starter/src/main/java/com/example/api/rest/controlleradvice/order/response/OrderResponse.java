package com.example.api.rest.controlleradvice.order.response;

import java.util.List;

import com.example.api.entity.Order;
import com.example.api.entity.OrderProduct;
import com.example.api.entity.Users;

public class OrderResponse {

    private int id;
    private double totalPrice;
    private Users user;
    private List<OrderProduct> orderProducts;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.user = order.getUser();
        this.orderProducts = order.getOrderProducts();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
}
