package com.codecool.shop.register;

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

    private static boolean invalidFields;
    public static boolean authentication = false;

    public static ModelAndView renderRegistrationPage(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("invalidFields", invalidFields);
        return new ModelAndView(params,"product/registration");
    }


    public static ModelAndView renderRegister(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();

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
        CustomerDao customerDataStore = CustomerDaoJdbc.getInstance();
        String firstName = req.queryParams("first_name");
        String lastName = req.queryParams("last_name");
        String email1 = req.queryParams("user_email1");
        String email2 = req.queryParams("user_email2");
        String password1 = req.queryParams("user_password1");
        String password2 = req.queryParams("user_password2");

        //firstName is given
        if(firstName.length()<1) {
            invalidFields = true;
            return false;
        }
        //lastName is given
        if(lastName.length()<1) {
            invalidFields = true;
            return false;
        }
        //check if email is in the database already
        if(customerDataStore.doesCustomerExist(email1)){
            invalidFields = true;
            return false;
        }
        //check if the two email fields match
        if(!email1.equals(email2)) {
            invalidFields = true;
            return false;
        }
        //check if the two password fields match
        if(!password1.equals(password2)) {
            invalidFields = true;
            return false;
        }
        return true;
    }

}
