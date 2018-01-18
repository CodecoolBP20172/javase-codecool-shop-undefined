package com.codecool.shop.utils;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Cart;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class JsonDataController {

    /**
     * Stores cart data from parsed json.
     *
     * @param  cart the cart in which we will store the parsed data.
     * @param  productDataStore from which we will find out which product to store.
     * @param  cartList parsable json string
     */
    public static void addToCartFromJson(Cart cart, ProductDao productDataStore, String cartList) throws IOException, DaoException {
        for (int i=0; i < parseJson(cartList).size(); i++) {
            cart.add(productDataStore.find(parseInt((String) parseJson(cartList).get(i).get("product_id"))), getQuantityFromJson(i, cartList));
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
    private static int getQuantityFromJson(int itemId, String cartList) throws IOException {
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
    public static List<Map<String, Object>> parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
    }
}
