package com.example.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import com.example.api.rest.ProductController;
import com.example.api.rest.controlleradvice.products.request.ProductRequest;
import com.example.api.rest.controlleradvice.products.response.ProductResponse;
import com.example.api.service.ProductService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class ProductControllerTest {

    @MockBean
    ProductRepository productRepository;
    @MockBean
    ProductService productService;
    @Autowired
    private WebTestClient webClient;

    Product product;
    ProductRequest productRequest;
    ProductResponse productResponse;
    Product updatedProduct;

    @BeforeEach
    public void setup() {
        webClient = WebTestClient.bindToController(new ProductController(productService)).build();
        product = new Product("Product 1", 100.0, 9);
        productRequest = new ProductRequest("Product 1", 100.0, 9);
        productResponse = new ProductResponse(product);
        updatedProduct = new Product("updatedName", 50.0, 150);

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "ADMIN")
    public void testGetProducts() throws Exception {
        // Mock data
        Product product1 = new Product("Product 1", 200.0, 5);
        Product product2 = new Product("Product 2", 100.0, 9);
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductResponse> productResponses = Arrays.asList(
                new ProductResponse(product1),
                new ProductResponse(product2)
        );

        // Mock service
        given(productService.findAll()).willReturn(products);
        given(productService.productsToResponses(products)).willReturn(productResponses);

        // Test and verify
        List<ProductResponse> response = webClient.get().uri("/api/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody()
                .collectList()
                .block();
        assertThat(response).usingRecursiveComparison().isEqualTo(productResponses);

    }

    @Test
    public void testGetProduct() throws Exception {
        // Mock data
        ProductResponse productResponse = new ProductResponse(product);

        // Mock service
        given(productService.findById(1)).willReturn(Optional.of(product));

        // Test and verify
        ProductResponse response = webClient.get().uri("/api/products/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody()
                .elementAt(0).
                block();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(productResponse);
    }

    @Test
    void addProductTest() {
        when(productService.requestToProduct(any(ProductRequest.class))).thenReturn(product);
        when(productService.save(any(Product.class))).thenReturn(product);

        webClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Product 1")
                .jsonPath("$.price").isEqualTo(100.0)
                .jsonPath("$.quantity").isEqualTo(9);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Mock service
        Mockito.doNothing().when(productService).deleteById(1);

        // Test and verify
        webClient.delete().uri("/api/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateProductTest() {
        // Given
        String id = "1";
        when(productService.updateProduct(any(ProductRequest.class), eq(id)))
                .thenReturn(updatedProduct);

        // When & Then
        webClient.put()
                .uri("/api/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productRequest), ProductRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponse.class)
                .value(response -> {
                    assertThat(response.getName()).isEqualTo("updatedName");
                    assertThat(response.getPrice()).isEqualTo(50.0);
                    assertThat(response.getQuantity()).isEqualTo(150);
                });
    }

}
