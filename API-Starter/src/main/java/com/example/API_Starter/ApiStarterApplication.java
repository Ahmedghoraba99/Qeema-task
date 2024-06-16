package com.example.API_Starter;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.API_Starter.entity.Product;
import com.example.API_Starter.entity.Users;
import com.example.API_Starter.service.ProductServiceImpl;
import com.example.API_Starter.service.UsersServiceImpl;

@SpringBootApplication
public class ApiStarterApplication {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UsersServiceImpl userService;

    public static void main(String[] args) {
        SpringApplication.run(ApiStarterApplication.class, args);

    }

    // CLI 
    @Bean
    public CommandLineRunner commandLineRunner(String[] args) {
        return runner -> {
            System.out.println("\n ********************* \n App started");
            System.out.println("Seeding data...");

            // Products seeds
            System.out.println("*****Product seeding*****");
            ArrayList<Product> products = new ArrayList<>();
            products.add(new Product("Apple", 100, 9));
            products.add(new Product("Banana", 200, 8));
            products.add(new Product("Cherry", 300, 7));
            products.forEach(product -> productService.add(product));

            // Users seeds
            System.out.println("*****Users seeding*****");
            ArrayList<Users> users = new ArrayList<>();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("123456789");
            users.add(new Users("Ahmed", "ahmed@gmail.com", encodedPassword, "ADMIN"));
            users.add(new Users("Mohamed", "mohamed@gmail.com", encodedPassword, "USER"));
            users.add(new Users("Ali", "ali@gmail.com", encodedPassword, "USER"));
            users.forEach(user -> userService.add(user));

        };
    }

}
