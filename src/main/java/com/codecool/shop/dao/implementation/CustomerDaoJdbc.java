package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.codecool.shop.connection.ConnectionManager.getConnection;

/**
 * {@inheritDoc}
 */
public class CustomerDaoJdbc implements CustomerDao{

    private static CustomerDaoJdbc instance = null;
    private Logger logger = LoggerFactory.getLogger(CustomerDaoJdbc.class);

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

    /**
     * Updates the customer details in the database.
     *
     * @param customer the customer to update.
     */
    @Override
    public void update(Customer customer) throws DaoException {

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
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Returns a customer from the database by the customers's id.
     *
     * @param id the id to find the exact customer.
     * @return Customer object.
     */
    @Override
    public Customer find(int id) throws DaoException {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "SELECT customer.id, first_name, last_name, phone_number, email, bill_country, \n" +
                            "bill_city, bill_zip, bill_address, ship_country, ship_city, ship_zip, ship_address \n" +
                            "FROM customer JOIN address ON (customer_id=customer.id) \n" +
                            "WHERE customer.id=?;");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                logger.info("Customer successfully returned");
                return createNewCustomerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return null;
    }

    /**
     * Returns all customers from the database as a list.
     *
     * @return List of all customers.
     */
    @Override
    public List<Customer> getCustomers() throws DaoException {

        String query = "SELECT customer.id, first_name, last_name, phone_number, email, bill_country,\n" +
                "bill_city, bill_zip, bill_address, ship_country, ship_city, ship_zip, ship_address\n" +
                "FROM customer JOIN address ON (customer.id = customer_id);";
        List<Customer> allCustomer = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()) {
                allCustomer.add(createNewCustomerFromResultSet(resultSet));
            }
            logger.debug("ALl customers successfully returned (size:{})", allCustomer.size());
            return allCustomer;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) throws DaoException {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "SELECT id, first_name, last_name, email, salt, hashed_password FROM customer\n" +
                            "WHERE email=?;");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Customer result = new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("salt"),
                        rs.getString("hashed_password"));
                logger.debug("Customer successfully returned (id:{})", result.getId());
                return result;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return null;
    }

    @Override
    public String getActualCustomerName(Integer id) throws DaoException {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement(
                    "SELECT first_name FROM customer WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String name = rs.getString("first_name");
                logger.debug("Customer name ({}) successfully returned", name);
                return name;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return null;
    }

    private Customer createNewCustomerFromResultSet(ResultSet resultSet) throws SQLException {
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
        result.setId(resultSet.getInt("id"));
        return result;
    }
}
