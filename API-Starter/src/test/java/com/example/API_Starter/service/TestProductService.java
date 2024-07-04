package com.example.API_Starter.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.API_Starter.entity.Product;
import com.example.API_Starter.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class TestProductService {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Test Product", 10.0, 5);
        product.setId(1);
    }

    @Test
    public void testGetProductById() {
        // Arrange
        when(productService.findById(1)).thenReturn(Optional.of(product));
        // Act
        Product result = productService.findById(1).get();
        // Assert
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(1);
        verify(productService, times(1)).findById(1);
    }

    @Test
    public void testDeleteProductById() {
        // Act
        productService.delete(product);
        // Assert
        verify(productService, times(1)).delete(product);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        when(productService.save(product)).thenReturn(product);
        String updatedName = "test2";
        // Act
        product.setName(updatedName);
        productService.save(product);
        // Assert
        verify(productService, times(1)).save(product);
        assertThat(product.getName()).isEqualTo(updatedName);
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        when(productService.save(product)).thenReturn(product);
        // Act
        productService.save(product);
        // Assert
        verify(productService, times(1)).save(product);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testFindAllProducts() {
        // Arrange
        Product product = new Product("Test Product", 10.0, 5);
        when(productService.findAll()).thenReturn(List.of(product));

        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertThat(result).containsExactly(product);
        verify(productService, times(1)).findAll();
    }

}
