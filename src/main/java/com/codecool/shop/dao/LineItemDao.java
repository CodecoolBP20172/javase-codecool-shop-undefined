package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

public interface LineItemDao {

    void add(Cart cart);
}
