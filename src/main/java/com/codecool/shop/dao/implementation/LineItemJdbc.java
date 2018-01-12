package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.ProductCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public Integer getLineItemsSubtotalByCustomer(Integer id) {

        int subtotal = 0;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT quantity, default_price, default_currency FROM line_item\n" +
                    "JOIN products ON (product_id=products.id)\n" +
                    "JOIN carts ON (line_item.cart_id=carts.id)\n" +
                    "WHERE carts.customer_id=? AND carts.id=(SELECT MAX(id) FROM carts WHERE customer_id=1);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int quantity = rs.getInt("quantity");
                int defaultPrice = rs.getInt("default_price");
                subtotal += quantity * defaultPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtotal;
    }
}
