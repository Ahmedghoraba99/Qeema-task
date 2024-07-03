package com.example.API_Starter.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Starter.entity.Product;
import com.example.API_Starter.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Iterable<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable("id") int id) {
        return productService.findById(id);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        product.setId(0);
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        productService.deleteById(id);
        return ResponseEntity
                .status(204)
                .build();
    }

    @PutMapping("/{id}")
    public Product putMethodName(@PathVariable String id, @RequestBody Product product) {
        product.setId(Integer.parseInt(id));
        return productService.save(product);
    }
}
