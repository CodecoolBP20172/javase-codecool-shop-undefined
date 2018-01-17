package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
import com.codecool.shop.model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class RegistrationController {

    public static ModelAndView renderRegistrationPage(Request req, Response res) {
        Map params = new HashMap<>();


        return new ModelAndView(params,"product/registration");
    }


    public static ModelAndView renderRegister(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();

        /*boolean isFieldMatching = false;

        if((req.queryParams("email1").equals(req.queryParams("email2")))){
            isFieldMatching = true;
        } else { isFieldMatching = false;}

        if((req.queryParams("password1").equals(req.queryParams("password2")))){
            isFieldMatching = true;
        } else { isFieldMatching = false;}

        */

        String password = req.queryParams("user_password1");
        //saltolni hashelni itt:
        password = BCrypt.hashpw(password, BCrypt.gensalt());



        Customer customer = new Customer(
                req.queryParams("user_name"),
                req.queryParams("user_email1"),
                password);


        customerJdbc.add(customer);



        // ide egy redirect kéne
        res.redirect("/");
        return null;

        //return new ModelAndView(params,"product/registration");
    }


}
