package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

public interface OrderDao {
    void add(Order product);
    Order find(int id);

    List<Order> getAll();
}
