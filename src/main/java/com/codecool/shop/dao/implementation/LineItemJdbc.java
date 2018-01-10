package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LineItemJdbc implements LineItemDao {

    private static LineItemJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private LineItemJdbc() {
    }

    public static LineItemJdbc getInstance() {
        if (instance == null) {
            instance = new LineItemJdbc();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        List<LineItem> lineItems = cart.getCART();
        Integer cartId = cart.getId();
        System.out.println(lineItems);
        System.out.println(cartId);
        for(LineItem lineItem: lineItems) {
            Integer productId = lineItem.getProduct().getId();
            Integer quantity = lineItem.getQuantity();
            System.out.println(productId);
            System.out.println(quantity);
            try {
                PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO line_item (cart_id, product_id, quantity) VALUES(?, ?, ?);");
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
