package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDaoMem implements CartDao {

    private List<Cart> CARTS = new ArrayList<>();
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
    public void add(Cart cart) {
        cart.setId(CARTS.size() + 1);
        CARTS.add(cart);
    }

    @Override
    public List<Cart> getCarts() {
        return CARTS;
    }

    @Override
    public List<Map> getActualUsersCart(int id) {
        return null;
    }
}
