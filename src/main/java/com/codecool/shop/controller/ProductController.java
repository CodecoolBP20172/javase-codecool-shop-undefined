package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;
import spark.ModelAndView;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        return new ModelAndView(params, "product/index");
    }


    public static ModelAndView renderCheckout(Request req, Response res) throws IOException {
        String cartList = req.queryParams("cart_list");
        Map params = new HashMap<>();
        params.put("cart_list", parseJson(cartList));
        //check cartList
        //System.out.println(parseJson(cartList));
        ProductDao productDataStore = ProductDaoMem.getInstance();

        //create cart
        /*this supposed to happen when you click proceed to payment,
        * and can give the customer id as an argument to the cartdaomem constructor
        * (now i passed in a '1')*/
        CartDao cart = new CartDaoMem(1);

        addToCartFromJson(cart, productDataStore, cartList);

        return new ModelAndView(params, "product/checkout");
    }

    private static void addToCartFromJson(CartDao cart, ProductDao productDataStore, String cartList) throws IOException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(Integer.parseInt((String) parseJson(cartList).get(i).get("product_id"))), quantity(i, cartList));
            //test
            System.out.println("Product: " + cart.getCart().get(i).product.getName());
            System.out.println("Quantity: " + cart.getCart().get(i).quantity);
            System.out.println("Price: " + cart.getCart().get(i).price);
        }
    }

    private static int quantity(int i, String cartList) throws IOException {
        try {
            return (int) parseJson(cartList).get(i).get("product_quantity");
        } catch (Exception e) {
            return 1;
        }
    }

    private static List<Map<String, Object>> parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
    }


}
