package com.example.API_Starter.rest;

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
import com.example.API_Starter.service.ProductServiceImpl;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Iterable<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        return productService.getById(id);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        product.setId(0);
        return productService.add(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Product putMethodName(@PathVariable String id, @RequestBody Product entity) {
        return productService.update(entity);
    }
}
