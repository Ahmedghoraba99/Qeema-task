package com.example.API_Starter.dao;

import org.springframework.stereotype.Repository;

import com.example.API_Starter.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Iterable<Product> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Products", Product.class)
                .getResultList();
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public Product save(Product entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Product entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(int id) {
        Product entity = findById(id);
        if (entity != null) {
            delete(entity);
        } else {
            throw new IllegalArgumentException("Entity not found");
        }
    }

    @Override
    public Product findByName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

}
