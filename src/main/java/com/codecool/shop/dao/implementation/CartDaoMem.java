package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<Product> CART = new ArrayList<>();

    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(CART.size() + 1);
        CART.add(product);
    }

    @Override
    public Product find(int id) {
        return CART.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

}
