package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

/**
 * Data access object interface to maintain orders in the database.
 * @since 1.0
 */
public interface OrderDao {
    void add(Order product);
    Order find(int id);

    List<Order> getAll();
}
