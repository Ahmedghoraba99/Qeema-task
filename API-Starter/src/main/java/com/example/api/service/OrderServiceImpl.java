package com.example.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Order;
import com.example.api.entity.OrderProduct;
import com.example.api.entity.Product;
import com.example.api.entity.Users;
import com.example.api.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UsersService usersService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, UsersService usersService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.usersService = usersService;
    }

    @Transactional
    public Order createOrder(long userId, List<OrderItemRequest> orderItemRequests) {
        Users user = findUserById(userId);
        Order order = initializeOrder(user);

        for (OrderItemRequest itemRequest : orderItemRequests) {
            Product product = findProductById(itemRequest.getProductId());
            validateProductQuantity(product, itemRequest.getQuantity());

            OrderProduct orderItem = createOrderProduct(order, product, itemRequest.getQuantity());
            order.getOrderProducts().add(orderItem);

            updateProductQuantity(product, itemRequest.getQuantity());
        }

        calculateTotalPrice(order);

        return orderRepository.save(order);
    }

    private Users findUserById(long userId) {
        return usersService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private Order initializeOrder(Users user) {
        Order order = new Order();
        order.setUser(user);
        return order;
    }

    private Product findProductById(long productId) {
        return productService.findById((int) productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
    }

    private void validateProductQuantity(Product product, int requestedQuantity) {
        if (product.getQuantity() < requestedQuantity) {
            throw new IllegalArgumentException("Insufficient quantity for product id: " + product.getId());
        }
    }

    private OrderProduct createOrderProduct(Order order, Product product, int quantity) {
        OrderProduct orderItem = new OrderProduct();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    private void updateProductQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productService.save(product);
    }

    private void calculateTotalPrice(Order order) {
        double totalPrice = order.getOrderProducts().stream()
                .mapToDouble(orderItem -> orderItem.getProduct().getPrice() * orderItem.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);
    }
}
