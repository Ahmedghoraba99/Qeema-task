package com.example.API_Starter.dao;

import com.example.API_Starter.entity.Product;

public interface ProductDao extends BasicCrudDao<Product> {

    Product findByName();
}
