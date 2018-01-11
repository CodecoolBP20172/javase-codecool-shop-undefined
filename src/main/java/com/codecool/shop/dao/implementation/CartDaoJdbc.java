package com.codecool.shop.dao.implementation;
import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.connection.ConnectionManager.getConnection;

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
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO carts (customer_id) VALUES(?);");
            ps.setInt(1, cart.getCustomerId());
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
        String query = "SELECT * FROM carts;";
        List<Cart> allCarts = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()) {
                Cart result = new Cart(resultSet.getInt("id"));
                allCarts.add(result);
            }
            return allCarts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
