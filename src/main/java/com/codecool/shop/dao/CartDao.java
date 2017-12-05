package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface CartDao {
    void add(Product product);
    Product find(int id);
}
