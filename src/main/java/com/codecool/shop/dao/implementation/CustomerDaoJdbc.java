package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.connection.ConnectionManager.getConnection;

public class CustomerDaoJdbc implements CustomerDao{

    private static CustomerDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CustomerDaoJdbc() {
    }

    public static CustomerDaoJdbc getInstance() {
        if (instance == null) {
            instance = new CustomerDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("UPDATE customer SET first_name=?, last_name=?, phone_number=?, email=?, bill_country=?, bill_city=?, bill_zip=?, bill_address=?, ship_country=?, ship_city=?, ship_zip=?, ship_address=? WHERE id=1;");
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getBillCountry());
            ps.setString(6, customer.getBillCity());
            ps.setInt(7, customer.getBillZip());
            ps.setString(8, customer.getBillAddress());
            ps.setString(9, customer.getShipCountry());
            ps.setString(10, customer.getShipCity());
            ps.setInt(11, customer.getShipZip());
            ps.setString(12, customer.getShipAddress());

            ps.execute();
            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM carts;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            customer.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Customer find(int id) {
        return null;
    }

    @Override
    public List<Customer> getCUSTOMERS() {

        String query = "SELECT * FROM customer;";
        List<Customer> allCustomer = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()) {
                Customer result = new Customer(resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("bill_country"),
                        resultSet.getString("bill_city"),
                        resultSet.getInt("bill_zip"),
                        resultSet.getString("bill_address"),
                        resultSet.getString("ship_country"),
                        resultSet.getString("ship_city"),
                        resultSet.getInt("ship_zip"),
                        resultSet.getString("ship_address"));
                allCustomer.add(result);
            }
            return allCustomer;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
