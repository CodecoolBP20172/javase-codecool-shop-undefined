package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

public interface OrderDao {
    void add(Order product);
    Order find(int id);
}
