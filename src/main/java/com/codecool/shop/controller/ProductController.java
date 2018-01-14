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

/**
 * Responsible for creating instances and gathering data to fill the site with.
 * Also gains useful formatted data from received json.
 *
 * @since 1.0
 */
public class ProductController {

    /**
     * Creates new product and product category instances.
     * Also gathers all products from a specific product category.
     *
     * @return a ModelView with params of map of all products by a product category and viewName.
     */
    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        return new ModelAndView(params, "product/index");
    }

    /**
     * Creates new instance of ProductDao, CartDao and LineItemDao.
     * Formats the receives data and stores it in the database.
     *
     * @return a ModelView with params of parsed json and viewName.
     */
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
        LineItemDao lineItemJdbc = LineItemDaoJdbc.getInstance();
        addToCartFromJson(cart, productDataStore, cartList);
        lineItemJdbc.add(cart);

        return new ModelAndView(params, "product/checkout");
    }

    /**
     * Creates new instance of ProductDao, CartDao and LineItemDao.
     * Formats the receives data and stores it in the database.
     *
     * @return a ModelView with params of parsed json and viewName.
     */
    public static ModelAndView renderConfirmation(Request req, Response res) {

        CartDao cartJdbc = CartDaoJdbc.getInstance();
        OrderDao orderJdbc = OrderDaoJdbc.getInstance();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();
        LineItemDao lineItemJdbc= LineItemDaoJdbc.getInstance();

        Order order = new Order(customerJdbc.find(1),cartJdbc.getCarts().get(cartJdbc.getCarts().size()-1));
        orderJdbc.add(order);

        Map params = new HashMap<>();
        params.put("sub_total", lineItemJdbc.getLineItemsSubtotalByCustomer(1));
        params.put("customer", order.getCustomer());
        params.put("cart_products", cartJdbc.getActualUsersCart(1));

        return new ModelAndView(params, "product/confirmation");
    }

    /**
     * Receives customer data and stores it in the database.
     * Also gets data for the Customer and LineItems connected to this order to count subtotal price.
     *
     * @return a ModelView with params of the orders subtotal price and viewName.
     */
    public static ModelAndView renderPayment(Request req, Response res) {
        Map params = new HashMap<>();
        CustomerDao customerJdbc = CustomerDaoJdbc.getInstance();
        LineItemDao lineItemJdbc= LineItemDaoJdbc.getInstance();

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
        customerJdbc.update(customer);

        params.put("sub_total", lineItemJdbc.getLineItemsSubtotalByCustomer(1));
        return new ModelAndView(params, "product/payment");
    }

    /**
     * Renders 404 error page.
     *
     * @return a ModelView with params of the error code and viewName.
     */
    public static ModelAndView renderError(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("error", 404);
        return new ModelAndView(params, "product/error");
    }

    /**
     * Stores cart data from parsed json.
     *
     * @param  cart the cart in which we will store the parsed data.
     * @param  productDataStore from which we will find out which product to store.
     * @param  cartList parsable json string
     */
    private static void addToCartFromJson(Cart cart, ProductDao productDataStore, String cartList) throws IOException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(parseInt((String) parseJson(cartList).get(i).get("product_id"))), quantity(i, cartList));
        }
    }

    /**
     * Counts how there are from one product in the cart.
     *
     * @param  itemId the id of the product.
     * @param  cartList json string of the cart to count from.
     *
     * @return either the products counted quantity if there are more or 1 if there aren't.
     */
    private static int quantity(int itemId, String cartList) throws IOException {
        try {
            return (int) parseJson(cartList).get(itemId).get("product_quantity");
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Parses through a json string to make it into readable cart data.
     *
     * @param  json parsable json string.
     *
     * @return either the products counted quantity if there are more or 1 if there aren't.
     */
    private static List<Map<String, Object>> parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
    }
}
