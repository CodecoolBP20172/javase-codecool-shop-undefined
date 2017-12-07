package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMem implements CustomerDao {

    private List<Customer> CUSTOMERS = new ArrayList<>();
    private static CustomerDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CustomerDaoMem() {
    }

    public static CustomerDaoMem getInstance() {
        if (instance == null) {
            instance = new CustomerDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {
        customer.setId(CUSTOMERS.size() + 1);
        CUSTOMERS.add(customer);
    }

    @Override
    public Customer find(int customerId) {
        return CUSTOMERS.stream().filter(t -> t.getId() == customerId).findFirst().orElse(null);
    }
}
