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
        System.out.println(parseJson(cartList));
        System.out.println(parseJson(cartList).get(0).get("product_id"));
        System.out.println(parseJson(cartList).get(1).get("product_id"));
        ProductDao productDataStore = ProductDaoMem.getInstance();

        //create cart
        CartDao cart = new CartDaoMem();

        addToCartFromJson(cart, productDataStore, cartList);

        testPrints(cart);

        return new ModelAndView(params, "product/checkout");
    }

    private static void testPrints(CartDao cart) {
        System.out.println("No. of items in cart: " + cart.getCart().size());
    }

    private static void addToCartFromJson(CartDao cart, ProductDao productDataStore, String cartList) throws IOException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(Integer.parseInt((String) parseJson(cartList).get(i).get("product_id"))));
            System.out.println("Product: " + cart.getCart().get(i).product.getName());
            System.out.println("Quantity: " + cart.getCart().get(i).quantity);
        }
    }

    private static List<Map<String, Object>> parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
    }

}
