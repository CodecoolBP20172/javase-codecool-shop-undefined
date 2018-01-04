package com.codecool.shop.login;
//import app.user.*;
//import app.util.*;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import spark.*;
import java.util.*;
//import static app.util.RequestUtil.*;



public class LoginController {

    public static ModelAndView renderLogin(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("wrongCredentials", false);
        params.put("category", "cat");
        params.put("products", "prod");
        return new ModelAndView(params, "product/login");
    }

    public static ModelAndView renderLoginAuthenticate(Request req, Response res) {



        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();



        Map params = new HashMap<>();

        if (authenticationSuccededCheck(req.queryParams("email"), req.queryParams("password"))){
            params.put("authentication", true);
            params.put("name", "Mate");
            params.put("category", productCategoryDataStore.find(1));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

            req.session().attribute("currentUser", "Mate");
            return new ModelAndView(params, "product/index");
        }

        params.put("wrongCredentials", true);
        return new ModelAndView(params, "product/login");
    }

    public static ModelAndView renderLogout(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        req.session().removeAttribute("currentUser");

        return new ModelAndView(params, "product/index");
    }

    public static Boolean authenticationSuccededCheck(String email, String password) {
        Map<String, String > userInfo = new HashMap<>();
        userInfo.put("email", "mate@gmail.com");
        userInfo.put("password", "anyad");
        if (userInfo.containsValue(email) && userInfo.containsValue(password)){
            return true;
        }
        return false;
    }

}

