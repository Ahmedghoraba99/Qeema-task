package com.example.API_Starter.service;

import java.util.List;

import com.example.API_Starter.entity.Product;
import com.example.API_Starter.repository.ProductRepository;
import com.example.API_Starter.rest.controlleradvice.products.request.ProductRequest;
import com.example.API_Starter.rest.controlleradvice.products.response.ProductResponse;

public interface ProductService extends ProductRepository {

    Product requestToProduct(ProductRequest productRequest);

    ProductResponse productToResponse(Product product);

    public List<ProductResponse> productsToResponses(List<Product> products);

    public Product updateProduct(ProductRequest productRequest, String id);
}
