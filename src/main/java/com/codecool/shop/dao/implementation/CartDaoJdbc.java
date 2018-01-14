package com.codecool.shop.dao.implementation;
import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codecool.shop.connection.ConnectionManager.getConnection;

/**
 * {@inheritDoc}
 */
public class CartDaoJdbc implements CartDao {
    private Logger logger = LoggerFactory.getLogger(CartDaoJdbc.class);
    private static Logger staticLogger = LoggerFactory.getLogger(CartDaoJdbc.class);

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

    /**
     * Adds a new cart to the database.
     *
     * @param cart the cart to add.
     */
    @Override
    public void add(Cart cart) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO carts (customer_id) VALUES(?);");
            ps.setInt(1, cart.getCustomerId());
            ps.execute();
            ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM carts;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            cart.setId(rs.getInt("id"));
            logger.debug("Cart (id: {}) successfully added to carts table in the database", cart.getId());
        } catch (SQLException e) {
            logger.error("Error while adding cart to the database. Message: {}", e.getMessage());
        }
    }

    /**
     * Returns all carts from the database as a list.
     *
     * @return List of all carts.
     */
    @Override
    public List<Cart> getCarts() {
        String query = "SELECT * FROM carts;";
        List<Cart> allCarts = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Cart result = new Cart(resultSet.getInt("customer_id"));
                allCarts.add(result);
                result.setId(resultSet.getInt("id"));
            }
            logger.info("Successfully returned all carts from the database");
            return allCarts;

        } catch (SQLException e) {
            logger.error("Error while reading data of carts table from the database. Message: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Returns the cart information of the actual user
     * by the user's id.
     *
     * @param id of the actual user.
     * @return Map with the cart details of the actual user.
     */
    @Override
    public List<Map> getActualUsersCart(int id) {
        List<Map> listOfProducts = new ArrayList<>();
        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement(
                    "SELECT products.id, products.name, default_price, default_currency, description, quantity FROM line_item\n" +
                    "JOIN products ON (product_id=products.id)\n" +
                    "JOIN carts ON (line_item.cart_id=carts.id)\n" +
                    "WHERE carts.customer_id=? AND carts.id=(SELECT MAX(id) FROM carts WHERE customer_id=1);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            logger.info("Successfully accessed database to return the cart of the actual user");

            while(rs.next()){

                Map<String, String> productDetails = new HashMap<>();
                productDetails.put("product_id", rs.getString("id"));
                productDetails.put("product_name", rs.getString("name"));
                productDetails.put("product_default_price", String.valueOf(rs.getInt("default_price")));
                productDetails.put("product_default_currency", rs.getString("default_currency"));
                productDetails.put("product_description", rs.getString("description"));
                productDetails.put("product_quantity", String.valueOf(rs.getInt("quantity")));
                listOfProducts.add(productDetails);
            }
        } catch (SQLException e) {
            logger.error("Error while reading user's cart information from the database. Message: {}", e.getMessage());
        }
        return listOfProducts;
    }
}
