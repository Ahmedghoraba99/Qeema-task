package com.example.API_Starter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Starter.entity.Product;
import com.example.API_Starter.repository.ProductRepository;
import com.example.API_Starter.rest.controlleradvice.products.request.ProductRequest;
import com.example.API_Starter.rest.controlleradvice.products.response.ProductResponse;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl {

    private ProductRepository productRepository;

    @Autowired
    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product requestToProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getQuantity()
        );
    }

    public ProductResponse productToResponse(Product product) {
        return new ProductResponse(product);
    }

    public List<ProductResponse> productsToResponses(List<Product> products) {
        return products.stream()
                .map(this::productToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Product updateProduct(ProductRequest productRequest, String id) {
        int productId = Integer.parseInt(id);
        productRepository.findById(productId).ifPresent(
                product -> {
                    product.setName(productRequest.getName());
                    product.setPrice(productRequest.getPrice());
                    product.setQuantity(productRequest.getQuantity());
                    productRepository.save(product);
                }
        );
        return productRepository.findById(productId).get();
    }

}
