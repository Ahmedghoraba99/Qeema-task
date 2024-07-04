package com.example.API_Starter.rest;

import java.util.List;

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
import com.example.API_Starter.rest.controlleradvice.products.request.ProductRequest;
import com.example.API_Starter.rest.controlleradvice.products.response.ProductResponse;
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
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> productResponses
                = productService.productsToResponses(
                        productService.findAll()
                );
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") int id) {
        Product product = productService.findById(id).get();
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest product) {
        Product savedProduct = productService.save(productService.requestToProduct(product));
        return ResponseEntity.ok(new ProductResponse(savedProduct));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> putMethodName(@PathVariable String id, @RequestBody ProductRequest product) {
        Product updatedProduct = productService.updateProduct(product, id);
        return ResponseEntity.ok(new ProductResponse(updatedProduct));
    }
}
