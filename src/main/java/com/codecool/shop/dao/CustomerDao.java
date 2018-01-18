package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

import java.util.List;

/**
 * Data access object interface to maintain customers in the database.
 * @since 1.0
 */
public interface CustomerDao {
    void add(Customer customer);
    void update(Customer customer) throws DaoException;
    Customer find(int id) throws DaoConnectionException, DaoException;
    Customer getCustomerByEmail(String email) throws DaoConnectionException, DaoException;
    public String getActualCustomerName(Integer id) throws DaoConnectionException, DaoException;

    List<Customer> getCustomers() throws DaoConnectionException, DaoException;
}