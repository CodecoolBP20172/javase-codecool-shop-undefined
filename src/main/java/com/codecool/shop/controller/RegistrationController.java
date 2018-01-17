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
    private static boolean isFieldValid;
    public static boolean authentication = false;

    public static ModelAndView renderRegistrationPage(Request req, Response res) {
        Map params = new HashMap<>();


        return new ModelAndView(params,"product/registration");
    }


    public static ModelAndView renderRegister(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();


        /*if((req.queryParams("email1").equals(req.queryParams("email2")))){
            isFieldMatching = true;
        } else { isFieldValid = false;}

        if((req.queryParams("password1").equals(req.queryParams("password2")))){
            isFieldValid = true;
        } else { isFieldValid = false;}

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

    private static boolean authenticate(Request req) {
        String firstName = req.queryParams("first_name");
        String lastName = req.queryParams("last_name");
        String email1 = req.queryParams("user_email1");
        String email2 = req.queryParams("user_email2");
        String password1 = req.queryParams("user_password1");
        String password2 = req.queryParams("user_password2");


        CustomerDao customerDataStore = CustomerDaoJdbc.getInstance();
        if(customerDataStore.doesCustomerExist(email1) == true){
            return false;}
        if((!email1.equals(email2)) || (!password1.equals(password2))){
            return false;}



       // throw new IllegalArgumentException("Illegal argument!");
        return true;
    }


}
