package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Order;

import java.util.List;

/**
 * Data access object interface to maintain orders in the database.
 * @since 1.0
 */
public interface OrderDao {
    void add(Order product) throws DaoConnectionException, DaoException;
    Order find(int id) throws DaoConnectionException, DaoException;

    List<Order> getAll() throws DaoConnectionException, DaoException;
}
