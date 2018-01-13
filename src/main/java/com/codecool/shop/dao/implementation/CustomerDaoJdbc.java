package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.codecool.shop.connection.ConnectionManager.getConnection;

public class CustomerDaoJdbc implements CustomerDao{

    private static CustomerDaoJdbc instance = null;
    private Logger logger = LoggerFactory.getLogger(CartDaoJdbc.class);

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

    }

    @Override
    public void update(Customer customer) {

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "UPDATE customer SET first_name=?, last_name=?, phone_number=?, email=?, bill_country=?, bill_city=?, bill_zip=?, bill_address=?, ship_country=?, ship_city=?, ship_zip=?, ship_address=? WHERE id=1;");
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
            ps =(ConnectionManager.getConnection()).prepareStatement(
                    "SELECT MAX(id) as id FROM carts;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            customer.setId(rs.getInt("id"));
            logger.debug("Customer (id: {}) successfully added to customers table in the database", customer.getId());
        } catch (SQLException e) {
            logger.error("Error while adding customer to the database. Message: {}", e.getMessage());
        }
    }

    @Override
    public Customer find(int id) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "SELECT * FROM customer WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Customer result = new Customer(rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("bill_country"),
                        rs.getString("bill_city"),
                        rs.getInt("bill_zip"),
                        rs.getString("bill_address"),
                        rs.getString("ship_country"),
                        rs.getString("ship_city"),
                        rs.getInt("ship_zip"),
                        rs.getString("ship_address"));
                result.setId(rs.getInt("id"));
                logger.debug("Customer successfully returned (id:{})", result.getId());
                return result;
            }
        } catch (SQLException e) {
            logger.error("Error while reading data from the database. Message: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> getCustomers() {

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
            logger.debug("ALl customers successfully returned (size:{})", allCustomer.size());
            return allCustomer;

        } catch (SQLException e) {
            logger.error("Error while reading data of customers table from the database. Message: {}", e.getMessage());
        }
        return null;
    }
}
