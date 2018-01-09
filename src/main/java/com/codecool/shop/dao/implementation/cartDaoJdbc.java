package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cartDaoJdbc implements CartDao {

    private List<Cart> CARTS = new ArrayList<>();
    private static cartDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private cartDaoJdbc() {
    }

    public static cartDaoJdbc getInstance() {
        if (instance == null) {
            instance = new cartDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        CARTS.add(cart);

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO carts (id) VALUES(?);");
            ps.setInt(1, cart.getId());
            ps.execute();
            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM carts;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            cart.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> getCart() {
        return CARTS;
    }
}
