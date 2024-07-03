package com.example.API_Starter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.OrderProduct;
import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;
import com.example.API_Starter.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl {

    private OrderRepository orderRepository;
    private ProductService productService;
    private UsersService usersService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, UsersService usersService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.usersService = usersService;
    }

    public OrderServiceImpl() {

    }

    @Transactional
    public Order createOrder(long userId, List<OrderItemRequest> orderItemRequests) {
        // Retrieve user from database
        Users user = usersService.findById(userId).get(); // Create new Order
        Order order = new Order();
        order.setUser(user);

        for (OrderItemRequest itemRequest : orderItemRequests) {
            // Product Id validity check
            Product product = productService.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + itemRequest.getProductId()));

            OrderProduct orderItem = new OrderProduct();
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Product quantity is insufficient");
            }
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            order.getOrderProducts().add(orderItem);
            // Decreased product quantity
            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productService.save(product);
        }
        order.setTotalPrice(userId);

        return orderRepository.save(order);
    }
}
