package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

import java.util.List;

public interface LineItemDao {

    void add(Cart cart);
    Integer getLineItemsSubtotalByCustomer(Integer id);
}
