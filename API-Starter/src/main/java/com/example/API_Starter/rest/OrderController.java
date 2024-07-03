package com.example.API_Starter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.service.OrderRequest;
import com.example.API_Starter.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;

    // Constructor injection    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> getOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable("id") int id) {
        return "Order with id: " + id;
    }

    @PostMapping("")
    public Order creatOrder(@RequestBody OrderRequest entity) {
        Order temp = orderService.createOrder(entity.getUserId(), entity.getOrderItems());
        return temp;
    }

}
