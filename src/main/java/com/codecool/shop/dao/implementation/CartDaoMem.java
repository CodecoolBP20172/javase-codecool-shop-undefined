package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CartDaoMem implements CartDao {

    private List<LineItem> CART = new ArrayList<>();
    int customerId;

    public CartDaoMem(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public void add(Product product, int quantity) {
        LineItem item = new LineItem(product);
        item.quantity = quantity;
        item.price *= quantity;
        CART.add(item);
    }

    @Override
    public List<LineItem> getCart() {
        return CART;
    }
}
