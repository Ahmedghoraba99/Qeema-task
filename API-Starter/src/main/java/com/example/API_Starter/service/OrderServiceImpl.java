package com.example.API_Starter.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.OrderProduct;
import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UsersServiceImpl usersService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UsersServiceImpl usersService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.usersService = usersService;
    }

    public OrderServiceImpl() {

    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllById(Iterable<Integer> ids) {
        return orderRepository.findAllById(ids);
    }

    public <S extends Order> List<S> saveAll(Iterable<S> entities) {
        return orderRepository.saveAll(entities);
    }

    public long count() {
        return orderRepository.count();
    }

    public void delete(Order entity) {
        orderRepository.delete(entity);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }

    public void deleteAll(Iterable<? extends Order> entities) {
        orderRepository.deleteAll(entities);
    }

    public <S extends Order, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        return orderRepository.findBy(example, queryFunction);
    }

    public <S extends Order> Optional<S> findOne(Example<S> example) {
        Optional<S> order = orderRepository.findOne(example);
        if (order.isPresent()) {
            return order;
        }
        return Optional.empty();
    }

    @Transactional
    public Order createOrder(int userId, List<OrderItemRequest> orderItemRequests) {
        // Retrieve user from database
        Users user = usersService.getById(userId);

        // Create new Order
        Order order = new Order();
        order.setUser(user);

        for (OrderItemRequest itemRequest : orderItemRequests) {
            // Product Id validity check
            Product product = productRepository.findById(itemRequest.getProductId())
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
            productRepository.save(product);
            // System.out.println("Product quantity: " + product.getQuantity());

        }
        order.setTotalPrice(userId);

        return orderRepository.save(order);
    }
}
