package com.codecool.shop.dao.implementation;
import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM carts;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            cart.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            return allCarts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> getActualUsersCart(int id) {
        List<Map> listOfProducts = new ArrayList<>();
        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT products.id, products.name, default_price, default_currency, description, quantity FROM line_item\n" +
                    "JOIN products ON (product_id=products.id)\n" +
                    "JOIN carts ON (line_item.cart_id=carts.id)\n" +
                    "WHERE carts.customer_id=? AND carts.id=(SELECT MAX(id) FROM carts WHERE customer_id=1);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Map<String, String> productDeatils = new HashMap<>();
                productDeatils.put("product_id", rs.getString("id"));
                productDeatils.put("product_name", rs.getString("name"));
                productDeatils.put("product_default_price", String.valueOf(rs.getInt("default_price")));
                productDeatils.put("product_default_currency", rs.getString("default_currency"));
                productDeatils.put("product_description", rs.getString("description"));
                productDeatils.put("product_quantity", String.valueOf(rs.getInt("quantity")));
                listOfProducts.add(productDeatils);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfProducts;
    }

}
