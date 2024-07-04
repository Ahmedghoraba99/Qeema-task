package com.example.API_Starter.repository;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.API_Starter.entity.Order;
import com.example.API_Starter.entity.OrderProduct;
import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TestOrderRepository {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    OrderProductRepository orderProductRepository;

    @Autowired
    TestOrderRepository(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderProductRepository orderProductRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderProductRepository = orderProductRepository;

    }

    @Test
    void testSaveOrder() {
        Users newUser = new Users("John Doe", "john@gmail.com", "1234567890");
        userRepository.save(newUser);
        Product product1 = new Product("Product 1", 100.00, 5);
        Product product2 = new Product("Product 2", 200.00, 5);
        productRepository.save(product1);
        productRepository.save(product2);

        Order order1 = new Order();
        order1.setUser(newUser);

        // order Item 1
        OrderProduct orderItem1 = new OrderProduct();
        orderItem1.setProduct(productRepository.findById(product1.getId()).get());
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order1);
        // orderItem 2 
        OrderProduct orderItem2 = new OrderProduct();
        orderItem2.setProduct(productRepository.findById(product2.getId()).get());
        orderItem2.setQuantity(1);
        orderItem2.setOrder(order1);
        order1.setOrderProducts(Arrays.asList(orderItem1, orderItem2));
        orderRepository.save(order1);
        //Assert
        assert orderRepository.findById(order1.getId()).isPresent();
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().size() == 2;
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().get(0).getQuantity() == 2;
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().get(1).getQuantity() == 1;

        // cleanup
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testDeleteOrder() {
        Users newUser = new Users("John Doe", "john@gmail.com", "1234567890");
        userRepository.save(newUser);
        Product product1 = new Product("Product 1", 100.00, 5);
        Product product2 = new Product("Product 2", 200.00, 5);
        productRepository.save(product1);
        productRepository.save(product2);

        Order order1 = new Order();
        order1.setUser(newUser);

        // order Item 1
        OrderProduct orderItem1 = new OrderProduct();
        orderItem1.setProduct(productRepository.findById(1).get());
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order1);
        orderRepository.save(order1);
        orderRepository.deleteById(order1.getId());
        // Assert
        assert orderRepository.findById(order1.getId()).isEmpty();

        // cleanup
        userRepository.deleteAll();
        productRepository.deleteAll();
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void testUpdateOrder() {
        Users newUser = new Users("John Doe", "john@gmail.com", "1234567890");
        userRepository.save(newUser);
        Users updatedUser = new Users("Mary Doe", "mary@gmail.com", "0987654321");
        userRepository.save(updatedUser); // Ensure the updated user is saved

        Product product1 = new Product("Product 1", 100.00, 5);
        Product product2 = new Product("Product 2", 200.00, 5);
        productRepository.save(product1);
        productRepository.save(product2);

        Order order1 = new Order();
        order1.setUser(newUser);

        // order Item 1
        OrderProduct orderItem1 = new OrderProduct();
        orderItem1.setProduct(productRepository.findById(product1.getId()).get());
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order1);

        // Ensure orderItem1 is added to order1's orderProducts list
        order1.getOrderProducts().add(orderItem1);

        orderRepository.save(order1);

        // Assert that order1 is saved with orderItem1
        assert orderRepository.findById(order1.getId()).isPresent();
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().size() == 1;
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().get(0).getQuantity() == 2;

        // Update the order
        order1.setUser(updatedUser);
        order1.getOrderProducts().get(0).setQuantity(1);

        orderRepository.save(order1);

        // Assert
        assert orderRepository.findById(order1.getId()).isPresent();
        assert orderRepository.findById(order1.getId()).get().getUser().getEmail().equals("mary@gmail.com");
        assert orderRepository.findById(order1.getId()).get().getOrderProducts().get(0).getQuantity() == 1;

        // cleanup
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        orderProductRepository.deleteAll();
    }

    @Test
    void testFindAllOrders() {
        Users testUser = userRepository.save(
                new Users("John Doe", "john@gmail.com", "1234567890")
        );

        productRepository.save(
                new Product("Product 1", 100.00, 5)
        );
        Order order = new Order();
        order.setUser(userRepository.findById((testUser.getId())).get());

        // order Item 1
        OrderProduct orderItem1 = new OrderProduct();
        orderItem1.setProduct(productRepository.findById(1).get());
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order);
        orderProductRepository.save(orderItem1);
        orderRepository.save(order);

        // Assert
        assert !orderRepository.findAll().isEmpty();
        assert orderRepository.findAll().size() == 1;
        // clean
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

}
