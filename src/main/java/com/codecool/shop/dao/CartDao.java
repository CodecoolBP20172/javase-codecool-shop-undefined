package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface CartDao {
    void add(Product product, int quantity);
    List<LineItem> getCart();
}
