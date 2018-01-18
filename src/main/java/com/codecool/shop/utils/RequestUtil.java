package com.codecool.shop.utils;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.ProductCategory;
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
            return req.session().attribute("productCategoryId");
        }
        return null;
    }

    public static void setProductCategoryIdToSession(Request req, Integer productCategoryId) {
        req.session().attribute("productCategoryId", productCategoryId);
    }

    public static void removeProductCategoryIdAttribute(Request req) {
        req.session().removeAttribute("productCategoryId");
    }

    public static void setCustomerIdToSession(Request req, Customer customer) {
        req.session().attribute("currentUser", customer.getId());
    }

    public static void removeSessionAttribute(Request req) {
        req.session().removeAttribute("currentUser");
    }
}
