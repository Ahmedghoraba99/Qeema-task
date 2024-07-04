package com.example.api.service;

import java.util.List;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import com.example.api.rest.controlleradvice.products.request.ProductRequest;
import com.example.api.rest.controlleradvice.products.response.ProductResponse;

public interface ProductService extends ProductRepository {

    Product requestToProduct(ProductRequest productRequest);

    ProductResponse productToResponse(Product product);

    public List<ProductResponse> productsToResponses(List<Product> products);

    public Product updateProduct(ProductRequest productRequest, String id);
}
