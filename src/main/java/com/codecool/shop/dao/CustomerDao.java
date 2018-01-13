package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

import java.util.List;

public interface CustomerDao {
    void add(Customer customer);
    void update(Customer customer);
    Customer find(int id);

    List<Customer> getCustomers();
}