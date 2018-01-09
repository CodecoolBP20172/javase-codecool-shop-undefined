package com.codecool.shop.dao.implementation;


import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {

    private List<Cart> CARTS = new ArrayList<>();
    private static CartDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoJdbc() {
    }

    public static CartDaoJdbc getInstance() {
        if (instance == null) {
            instance = new CartDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO cart (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES(?,?,?,?,?,?);");
            ps.setString(1, cart.);
            ps.setDouble(2, Double.parseDouble(cart.getPriceWithoutCurrency()));
            ps.setString(3, String.valueOf(product.getDefaultCurrency()));
            ps.setInt(4, product.getSupplier().getId());
            ps.setInt(5, product.getProductCategory().getId());
            ps.setString(6, product.getDescription());
            ps.execute();
            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM products;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            product.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> getCart() {
        return CARTS;
    }
}
