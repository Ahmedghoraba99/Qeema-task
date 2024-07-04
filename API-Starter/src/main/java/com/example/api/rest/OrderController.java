package com.example.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Order;
import com.example.api.rest.controlleradvice.order.request.OrderRequest;
import com.example.api.rest.controlleradvice.order.response.OrderResponse;
import com.example.api.service.OrderService;

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
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = orderService.ordersToResponses();
        return ResponseEntity.ok(orders);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.findById(id).get();
        return ResponseEntity.ok(new OrderResponse(order));
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> creatOrder(@RequestBody OrderRequest entity) {
        Order createdOrder = orderService.createOrder(entity.getUserId(), entity.getOrderItems());
        return ResponseEntity.ok(new OrderResponse(createdOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
