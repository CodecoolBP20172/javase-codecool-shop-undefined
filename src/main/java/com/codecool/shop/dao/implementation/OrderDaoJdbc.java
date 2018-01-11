package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.ArrayList;
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
        System.out.println("CUSTOMER ID :" + customer_id);
        int cartId = order.getCart().getId();

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO orders (customer_id, cart_id) VALUES(?,?);");
            ps.setInt(1, customer_id);
            ps.setDouble(2, cartId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Order find(int id) {
        String query = "SELECT * FROM orders WHERE id ='" + id + "';";

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
            return allOrder;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
