package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class LineItemDaoJdbc implements LineItemDao {

    private static LineItemDaoJdbc instance = null;
    private Logger logger = LoggerFactory.getLogger(LineItemDaoJdbc.class);

    /* A private Constructor prevents any other class from instantiating.
     */
    private LineItemDaoJdbc() {
    }

    public static LineItemDaoJdbc getInstance() {
        if (instance == null) {
            instance = new LineItemDaoJdbc();
        }
        return instance;
    }

    /**
     * Adds new line item(s) to the database.
     *
     * @param cart the cart containing the line item(s) to add.
     */
    @Override
    public void add(Cart cart) {
        List<LineItem> lineItems = cart.getCART();
        Integer cartId = cart.getId();
        for(LineItem lineItem: lineItems) {
            Integer productId = lineItem.getProduct().getId();
            Integer quantity = lineItem.getQuantity();
            try {
                PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                        "INSERT INTO line_item (cart_id, product_id, quantity) VALUES(?, ?, ?);");
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.execute();
            } catch (SQLException e) {
                logger.error("Error while adding line item to the database. Message: {}", e.getMessage());
            }
        }
    }

    /**
     * Returns the current customer's subtotal of line items.
     *
     * @param id the id of the current user.
     * @return Integer of subtotal.
     */
    @Override
    public Integer getLineItemsSubtotalByCustomer(Integer id) {

        int subtotal = 0;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "SELECT quantity, default_price, default_currency FROM line_item\n" +
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
            logger.debug("Line items subtotal successfully returned ({})", subtotal);
        } catch (SQLException e) {
            logger.error("Error while reading data from the database. Message: {}", e.getMessage());
        }
        return subtotal;
    }
}
