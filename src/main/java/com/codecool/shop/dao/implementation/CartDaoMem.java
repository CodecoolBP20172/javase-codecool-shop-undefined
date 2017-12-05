package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<LineItem> CART = new ArrayList<>();

    @Override
    public void add(Product product) {
        LineItem item = new LineItem(product);
        CART.add(item);
    }

    @Override
    public List<LineItem> getCart() {
        return CART;
    }

}
