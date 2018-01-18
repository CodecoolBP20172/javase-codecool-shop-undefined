package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Data access object interface to maintain carts in the database.
 * @since 1.0
 */
public interface CartDao {
    void add(Cart cart) throws DaoException;
    List<Cart> getCarts() throws DaoConnectionException, DaoException;
    List<Map> getActualUsersCart(int id) throws DaoConnectionException, DaoException;
}
