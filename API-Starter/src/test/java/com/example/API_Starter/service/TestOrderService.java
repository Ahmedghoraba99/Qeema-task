package com.example.API_Starter.service;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;
import com.example.API_Starter.repository.OrderRepository;
import com.example.API_Starter.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UsersService usersService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Users user;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        user = new Users("John Doe", "john.doe@example.com", "password1234");
        product1 = new Product("test1", 33.5, 10);
        product2 = new Product("test2", 50.5, 1);

        product1.setId(1);
        product2.setId(2);
    }

    @Test
    void testCreateOrder() {
        long userId = 1L;
        OrderItemRequest itemRequest1 = new OrderItemRequest(1, 1, 2);
        OrderItemRequest itemRequest2 = new OrderItemRequest(1, 2, 1);

        when(usersService.findById(userId)).thenReturn(Optional.of(user));
        when(productService.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productService.findById(product2.getId())).thenReturn(Optional.of(product2));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(userId, Arrays.asList(itemRequest1, itemRequest2));

        assertEquals(2, order.getOrderProducts().size());
        assertEquals(user, order.getUser());
        assertEquals(117.5, order.getTotalPrice());

        verify(productService, times(1)).save(product1);
        verify(productService, times(1)).save(product2);
        assertEquals(8, product1.getQuantity());
        assertEquals(0, product2.getQuantity());
    }

    @Test
    void testCreateOrderUserNotFound() {
        long userId = 1L;
        OrderItemRequest itemRequest1 = new OrderItemRequest(1, 1, 2);

        when(usersService.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            orderService.createOrder(userId, Arrays.asList(itemRequest1));
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateOrderProductNotFound() {
        long userId = 1L;
        productRepository.delete(product1);
        OrderItemRequest itemRequest1 = new OrderItemRequest(1, product1.getId(), 2);

        when(usersService.findById(userId)).thenReturn(Optional.of(user));
        when(productService.findById(product1.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            orderService.createOrder(userId, Arrays.asList(itemRequest1));
        });

        assertEquals("Product not found with id: " + product1.getId(), exception.getMessage());
    }

    @Test
    void testCreateOrderInsufficientQuantity() {
        long userId = 1L;
        OrderItemRequest itemRequest1 = new OrderItemRequest(1, 1, 20);

        when(usersService.findById(userId)).thenReturn(Optional.of(user));
        when(productService.findById(product1.getId())).thenReturn(Optional.of(product1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(userId, Arrays.asList(itemRequest1));
        });

        assertEquals("Insufficient quantity for product id: " + product1.getId(), exception.getMessage());
    }
}
