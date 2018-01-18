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

import static com.codecool.shop.utils.RequestUtil.*;

public class SortingController {


    public static ModelAndView renderProductCategory(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        setProductCategoryIdToSession(req, productCategoryDataStore.getIdByName(req.params(":name")));
        res.redirect("/");
        return null;
    }

    public static ModelAndView renderAllProductCategory(Request req, Response res) {
        removeProductCategoryIdAttribute(req);
        res.redirect("/");
        return null;
    }





}
