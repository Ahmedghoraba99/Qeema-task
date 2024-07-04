package com.example.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.api.rest.AuthController;
import com.example.api.rest.OrderController;
import com.example.api.rest.ProductController;
import com.example.api.rest.UsersController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiStarterApplicationTests {

    @Autowired
    private AuthController authController;
    @Autowired
    private UsersController usersController;
    @Autowired
    private ProductController productController;
    @Autowired
    private OrderController orderController;

    @LocalServerPort
    private int port;

    @Test
    void testControllers() {
        assertThat(authController).isNotNull();
        assertThat(usersController).isNotNull();
        assertThat(productController).isNotNull();
        assertThat(orderController).isNotNull();
    }

    @Test
    void testNames() {
        assertThat(authController.getClass().getSimpleName()).isEqualTo("AuthController");
        assertThat(usersController.getClass().getSimpleName()).isEqualTo("UsersController");
        assertThat(productController.getClass().getSimpleName()).isEqualTo("ProductController");
        assertThat(orderController.getClass().getSimpleName()).isEqualTo("OrderController");
    }

    @Test
    void contextLoads() throws Exception {
        // ensure application context loads correctly

    }
}
