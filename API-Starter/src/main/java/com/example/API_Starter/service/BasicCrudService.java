package com.example.API_Starter.service;

public interface BasicCrudService<T> {

    public Iterable<T> getAll();

    public T getById(int id);

    public T add(T user);

    public T update(T user);

    public void delete(int id);
}
