package com.example.API_Starter.dao;

public interface BasicCrudDao<T> {

    Iterable<T> findAll();

    T findById(int id);

    T save(T entity);

    void delete(T entity);

    void deleteById(int id);

}
