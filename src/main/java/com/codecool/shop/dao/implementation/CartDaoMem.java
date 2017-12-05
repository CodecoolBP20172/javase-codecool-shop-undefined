package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartDaoMem implements CartDao {

    private List<LineItem> CART = new ArrayList<>();

    @Override
    public void add(Product product) {
        LineItem item = new LineItem(product);
        if (CART.size()==0) {
            CART.add(item);
            return;
        } else {
            for (int prod = 0; prod < CART.size(); prod++) {
                checkQuantity(prod, item);
            }
        }
        CART.add(item);
    }

    @Override
    public List<LineItem> getCart() {
        return CART;
    }

    private void checkQuantity(int prod, LineItem item) {
        if (Objects.equals(CART.get(prod).product.getName(), item.product.getName())) {
            CART.get(prod).quantity += 1;
            return;
        }
    }

}
