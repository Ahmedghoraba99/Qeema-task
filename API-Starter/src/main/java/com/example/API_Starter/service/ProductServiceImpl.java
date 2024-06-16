package com.example.API_Starter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Starter.entity.Product;

// @Service
// public class ProductServiceImpl implements ProductService {
//     private ProductDao productDao;
//     @Autowired
//     public ProductServiceImpl(ProductDao productDao) {
//         this.productDao = productDao;
//     }
//     @Override
//     public Iterable<Product> getAll() {
//         return productDao.findAll();
//     }
//     @Override
//     public Product getById(int id) {
//         return productDao.findById(id);
//     }
//     @Override
//     public Product add(Product product) {
//         return productDao.save(product);
//     }
//     @Override
//     public Product update(Product user) {
//         return productDao.save(user);
//     }
//     @Override
//     public void delete(int id) {
//         productDao.deleteById(id);
//     }
// }
@Service
public class ProductServiceImpl {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> getAll() {
        System.out.println("Getting all products");
        return productRepository.findAll();
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsById(List<Integer> ids) {
        return productRepository.findAllById(ids);
    }

}
