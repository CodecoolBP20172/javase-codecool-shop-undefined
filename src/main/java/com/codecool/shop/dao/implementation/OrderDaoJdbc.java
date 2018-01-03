package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.List;

import static com.codecool.shop.connection.ConnectionManager.getConnection;


public class OrderDaoJdbc implements OrderDao {


    private static OrderDaoJdbc instance = null;

    public static OrderDaoJdbc getInstance() {
        if (instance == null) {
            instance = new OrderDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        int customer_id = order.getCustomerId();
        double subtotal = order.getCart().getSubTotal();

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO orders (customer_id, subtotal) VALUES(?,?);");
            ps.setInt(1, customer_id);
            ps.setDouble(2, subtotal);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //sheit
    @Override
    public Order find(int customer_id) {
        String query = "SELECT * FROM orders WHERE customer_id ='" + customer_id + "';";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()){
                Order result = new Order(resultSet.getInt("customer_id"));
                return result;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //sheit
    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        executeQuery(query);
        return null;
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
