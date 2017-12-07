package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

public interface CustomerDao {
    void add(Product product);
    Product find(int id);
}