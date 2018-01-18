package com.codecool.shop.login;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import spark.*;
import java.util.*;

import static com.codecool.shop.utils.RequestUtil.removeSessionAttribute;
import static com.codecool.shop.utils.RequestUtil.setCustomerIdToSession;

public class LoginController {

    private static boolean wrongCredentials;

    public static ModelAndView renderLogin(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("wrongCredentials", wrongCredentials);
        return new ModelAndView(params, "product/login");
    }

    public static ModelAndView renderLoginAuthentication(Request req, Response res) throws DaoException {

        if (authenticate(req.queryParams("email"), req.queryParams("password"), req)){
            wrongCredentials = false;
            res.redirect("/");
            return null;
        }
        wrongCredentials = true;
        res.redirect("/login");
        return null;
    }

    public static ModelAndView renderLogout(Request req, Response res) {
        removeSessionAttribute(req);
        res.redirect("/");
        return null;
    }

    private static boolean authenticate(String email, String password, Request req) throws DaoException {
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

