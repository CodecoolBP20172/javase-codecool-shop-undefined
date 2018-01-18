package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static com.codecool.shop.utils.RequestUtil.getCustomerIdFromSession;
import static com.codecool.shop.utils.RequestUtil.getProductCategoryIdFromSession;
import static com.codecool.shop.utils.RequestUtil.setCustomerIdToSession;

public class SortingController {


    private static Boolean isSortingValid(Request req) {
        /*ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();

        if (productCategoryDataStore.getAllNames()) {
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
*/
        return null;
    }


    public static ModelAndView renderProductCategory(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        req.session().attribute("productCategoryId", productCategoryDataStore.getIdByName(req.params(":name")));
        res.redirect("/");
        return null;





    }





}
