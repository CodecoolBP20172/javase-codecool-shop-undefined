package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

public interface CustomerDao {
    void add(Customer customer);
    Customer find(int id);
}