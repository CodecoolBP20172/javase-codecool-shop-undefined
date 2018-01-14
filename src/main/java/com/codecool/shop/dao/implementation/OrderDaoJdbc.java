package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.connection.ConnectionManager.getConnection;

/**
 * {@inheritDoc}
 */
public class OrderDaoJdbc implements OrderDao {
    private Logger logger = LoggerFactory.getLogger(OrderDaoJdbc.class);
    private static Logger staticLogger = LoggerFactory.getLogger(OrderDaoJdbc.class);

    private static OrderDaoJdbc instance = null;

    public static OrderDaoJdbc getInstance() {
        if (instance == null) {
            instance = new OrderDaoJdbc();
        }
        return instance;
    }

    /**
     * Adds a new order to the database.
     *
     * @param order the order to add.
     */
    @Override
    public void add(Order order) {
        int customer_id = order.getCustomerId();
        System.out.println("CUSTOMER ID :" + customer_id);
        int cartId = order.getCart().getId();

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO orders (customer_id, cart_id) VALUES(?,?);");
            ps.setInt(1, customer_id);
            ps.setDouble(2, cartId);
            ps.execute();
            logger.debug("Order (id: {}) successfully added to orders table in the database", order.getId());
        } catch (SQLException e) {
            logger.error("Error while adding order to the database. Message: {}", e.getMessage());
        }

    }

    /**
     * Returns one specific order.
     *
     * @param id id of the order.
     * @return an order with the param user id reference.
     */
    @Override
    public Order find(int id) {
        String query = "SELECT * FROM orders WHERE id ='" + id + "';";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()){
                Order result = new Order(resultSet.getInt("customer_id"));
                logger.debug("Order successfully found in orders table in the database by customer id({})", id);
                return result;
            } else {
                return null;
            }

        } catch (SQLException e) {
            logger.error("Error while searching for order in the database. Message: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Returns all orders from the database as a list.
     *
     * @return List of all orders.
     */
    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        List<Order> allOrder = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()) {
                Order result = new Order(resultSet.getInt("customer_id"));
                allOrder.add(result);
            }
            logger.debug("All order's data successfully from orders table in the database");
            return allOrder;

        } catch (SQLException e) {
            logger.error("Error while reading order data from the database. Message: {}", e.getMessage());
        }
        return null;
    }

}
