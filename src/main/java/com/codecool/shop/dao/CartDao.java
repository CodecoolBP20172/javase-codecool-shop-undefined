package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartDao {
    void add(Cart cart);
    List<Cart> getCarts();
    List<Map> getActualUsersCart(int id);
}
