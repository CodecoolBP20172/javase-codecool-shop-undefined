package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

import java.util.List;

/**
 * Data access object interface to maintain line items in the database.
 * @since 1.0
 */
public interface LineItemDao {

    void add(Cart cart) throws DaoConnectionException, DaoException;
    Integer getLineItemsSubtotalByCustomer(Integer id) throws DaoConnectionException, DaoException;
}
