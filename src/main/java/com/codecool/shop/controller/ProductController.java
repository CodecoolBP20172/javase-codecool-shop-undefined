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
        
        ProductDao productDataStore = ProductDaoMem.getInstance();

        //create cart
        CartDao cartMem = CartDaoMem.getInstance();
        Cart cart = new Cart(1);
        cartMem.add(cart);

        addToCartFromJson(cartMem, cart, productDataStore, cartList);

        return new ModelAndView(params, "product/checkout");
    }

    public static ModelAndView renderConfirmation(Request req, Response res) {

        CartDao cartMem = CartDaoMem.getInstance();
        OrderDao orderMem = OrderDaoMem.getInstance();
        CustomerDao customerMem = CustomerDaoMem.getInstance();

        Order order = new Order(customerMem.getCUSTOMERS().get(0),cartMem.getCart().get(0));
        orderMem.add(order);
        Map params = new HashMap<>();
        params.put("sub_total", cartMem.getCart().get(0).getSubTotal());
        params.put("customer", order.getCustomer());
        System.out.println(orderMem.getAll().get(0));
        params.put("cart_products", cartMem.getCart().get(0).getCART());
        return new ModelAndView(params, "product/confirmation");
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerMem = CustomerDaoMem.getInstance();
        Customer customer = new Customer(
                req.queryParams("firstname"),
                req.queryParams("lastname"),
                req.queryParams("phonenumber"),
                req.queryParams("email"),
                req.queryParams("bcountry"),
                req.queryParams("bcity"),
                req.queryParams("bzip"),
                req.queryParams("badress"),
                req.queryParams("shcountry"),
                req.queryParams("shcity"),
                req.queryParams("shzip"),
                req.queryParams("shaddress")
        );
        customerMem.add(customer);

        System.out.println(customerMem);
        System.out.println(customer);
        params.put("cart_list", "payment");
        return new ModelAndView(params, "product/payment");
    }

    private static void addToCartFromJson(CartDao cartMem, Cart cart, ProductDao productDataStore, String cartList) throws IOException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(Integer.parseInt((String) parseJson(cartList).get(i).get("product_id"))), quantity(i, cartList));
            //test
            System.out.println("Product: " + cartMem.getCart().get(0).getCART().get(i).getProduct().getName());
            System.out.println("Quantity: " + cart.getCART().get(i).quantity);
            System.out.println("Price: " + cart.getCART().get(i).price);
            System.out.println("memory cart:" + cartMem.getCart().get(0).getCART().get(i));
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
