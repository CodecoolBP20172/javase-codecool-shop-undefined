package com.codecool.shop.utils;

import com.codecool.shop.model.Customer;
import spark.Request;

public class RequestUtil {

    public static Integer getCustomerIdFromSession(Request req) {
        if (req.session().attribute("currentUser") != null) {
            return req.session().attribute("currentUser");
        }
        return null;
    }

    public static Integer getProductCategoryIdFromSession(Request req) {
        if (req.session().attribute("productCategoryId") != null) {
            return req.session().attribute("productCategory");
        }
        return null;
    }

    public static void setProductCategoryIdToSession(Request req, Customer customer) {
        req.session().attribute("productCategoryId", customer.getId());
    }

    public static void removeProductCategoryIdAttribute(Request req) {
        req.session().removeAttribute("ProductCategoryId");
    }

    public static void setCustomerIdToSession(Request req, Customer customer) {
        req.session().attribute("currentUser", customer.getId());
    }

    public static void removeSessionAttribute(Request req) {
        req.session().removeAttribute("currentUser");
    }
}
