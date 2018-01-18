package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.*;
import spark.Request;

import static com.codecool.shop.utils.RequestUtil.setCustomerIdToSession;

public class SortingController {

    private static boolean isSortingValid(, Request req) {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        CustomerDao customerDataStore = CustomerDaoJdbc.getInstance();
        Customer customer = customerDataStore.getCustomerByEmail(email);
        if (customer == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, customer.getSalt());
        if (hashedPassword.equals(customer.getHashedPassword())) {
            setCustomerIdToSession(req, customer);
            return true;
        }
        throw new IllegalArgumentException("Illegal argument!");
    }

}
