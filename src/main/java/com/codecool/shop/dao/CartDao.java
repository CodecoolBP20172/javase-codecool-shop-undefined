package com.codecool.shop.dao;

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
    void add(Cart cart);
    List<Cart> getCarts();
    List<Map> getActualUsersCart(int id);
}
