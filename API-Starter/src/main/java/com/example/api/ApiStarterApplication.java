package com.example.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.api.entity.Product;
import com.example.api.entity.Users;
import com.example.api.service.ProductService;
import com.example.api.service.UsersService;

@SpringBootApplication
public class ApiStarterApplication {

    @Autowired
    private ProductService productService;

    @Autowired
    private @Lazy
    UsersService userService;

    public static void main(String[] args) {
        SpringApplication.run(ApiStarterApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationArguments args) {
        return runnerArgs -> {
            if (args.containsOption("seed")) {
                System.out.println("\n ********************* \n App started");
                System.out.println("Seeding data...");

                // Products seeds
                System.out.println("*****Product seeding*****");
                ArrayList<Product> products = new ArrayList<>();
                products.add(new Product("Apple", 100, 9));
                products.add(new Product("Banana", 200, 8));
                products.add(new Product("Cherry", 300, 7));
                products.forEach(product -> productService.save(product));

                // Users seeds
                System.out.println("*****Users seeding*****");
                ArrayList<Users> users = new ArrayList<>();
                String encodedPassword = passwordEncoder().encode("123456789");
                users.add(new Users("Ahmed", "ahmed@gmail.com", encodedPassword, "ADMIN"));
                users.add(new Users("Mohamed", "mohamed@gmail.com", encodedPassword, "USER"));
                users.add(new Users("Ali", "ali@gmail.com", encodedPassword, "USER"));
                users.forEach(user -> userService.save(user));
            }
        };
    }
}
