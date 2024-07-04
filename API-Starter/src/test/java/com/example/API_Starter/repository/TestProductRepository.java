package com.example.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.api.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TestProductRepository {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAllProducts() {
        // Arrange
        Product product1 = new Product("Product 1", 10.99, 4);
        Product product2 = new Product("Product 2", 19.99, 2
        );
        // Act
        Product product3 = new Product("Product 3", 29.99, 1);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        // Assert

        assert (productRepository.findAll().size() == 3);
        assertThat(productRepository.findAll()).containsExactlyInAnyOrder(product1, product2, product3);
        // clean
        productRepository.deleteAll();
    }

    @Test
    void testFindProductById() {
        // Arrange
        Product product1 = new Product("Product 1", 10.99, 4);
        Product product2 = new Product("Product 2", 19.99, 2
        );
        // Act  
        productRepository.save(product1);
        productRepository.save(product2);
        // Assert
        assertThat(productRepository.findById(product1.getId())).isPresent();
        assertThat(productRepository.findById(product1.getId())).get().isEqualTo(product1);
        // clean
        productRepository.deleteAll();
    }

    @Test
    void testSaveProduct() {
        // Arrange
        Product product1 = new Product("Product 1", 10.99, 4);
        // Act
        productRepository.save(product1);
        // Assert
        assertThat(productRepository.findById(product1.getId())).isPresent();
        assertThat(productRepository.findById(product1.getId())).get().isEqualTo(product1);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product1 = new Product("Product 1", 10.99, 4);
        // Act
        productRepository.save(product1);
        product1.setName("Product 1 Updated");
        productRepository.save(product1);
        // Assert
        assertThat(productRepository.findById(product1.getId())).isPresent();
        assertThat(productRepository.findById(product1.getId())).get().isEqualTo(product1);
        // clean
        productRepository.deleteAll();
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Product product1 = new Product("Product 1", 10.99, 4);
        // Act
        productRepository.save(product1);
        productRepository.deleteById(product1.getId());
        // Assert
        assertThat(productRepository.findById(product1.getId())).isNotPresent();
    }

}
