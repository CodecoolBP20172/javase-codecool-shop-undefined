package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        return new ModelAndView(params, "product/index");
    }


    public static ModelAndView renderCheckout(Request req, Response res) throws IOException {
        String cartList = req.queryParams("cart_list");
        Map params = new HashMap<>();
        params.put("cart_list", parseJson(cartList));
        
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Integer customerIdFromSession = 1;

        //create cart
        CartDao cartJdbc = CartDaoJdbc.getInstance();
        Cart cart = new Cart(customerIdFromSession);
        cartJdbc.add(cart);

        //create line items
        LineItemDao lineItemJdbc = LineItemJdbc.getInstance();
        addToCartFromJson(cartJdbc, cart, productDataStore, cartList);
        lineItemJdbc.add(cart);

        return new ModelAndView(params, "product/checkout");
    }

    public static ModelAndView renderConfirmation(Request req, Response res) {

        CartDao cartJdbc = CartDaoJdbc.getInstance();
        OrderDao orderJdbc = OrderDaoJdbc.getInstance();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();
        LineItemDao lineItemJdbc= LineItemJdbc.getInstance();

        Order order = new Order(customerJdbc.getCUSTOMERS().get(0),cartJdbc.getCarts().get(cartJdbc.getCarts().size()-1));
        orderJdbc.add(order);

        Map params = new HashMap<>();
        params.put("sub_total", lineItemJdbc.getLineItemsSubtotalByCustomer(1));
        params.put("customer", order.getCustomer());
        params.put("cart_products", cartJdbc.getActualUsersCart(1));

        return new ModelAndView(params, "product/confirmation");
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();
        LineItemDao lineItemJdbc= LineItemJdbc.getInstance();

        Customer customer = new Customer(
                req.queryParams("firstname"),
                req.queryParams("lastname"),
                req.queryParams("phonenumber"),
                req.queryParams("email"),
                req.queryParams("bcountry"),
                req.queryParams("bcity"),
                parseInt(req.queryParams("bzip")),
                req.queryParams("badress"),
                req.queryParams("shcountry"),
                req.queryParams("shcity"),
                parseInt(req.queryParams("shzip")),
                req.queryParams("shaddress")
        );
        customerJdbc.add(customer);

        params.put("sub_total", lineItemJdbc.getLineItemsSubtotalByCustomer(1));
        return new ModelAndView(params, "product/payment");
    }

    public static ModelAndView renderError(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("error", 404);
        return new ModelAndView(params, "product/error");
    }

    private static void addToCartFromJson(CartDao cartJdbc, Cart cart, ProductDao productDataStore, String cartList) throws IOException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(parseInt((String) parseJson(cartList).get(i).get("product_id"))), quantity(i, cartList));
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
