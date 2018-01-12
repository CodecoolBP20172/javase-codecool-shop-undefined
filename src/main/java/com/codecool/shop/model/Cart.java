package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/** Represents a cart.
 * A cart contains a list of lineItem-s which contain the ordered products,
 * their quantities and price.
 * The instance also contains a reference to the customer.
 *
 * @since 1.0
 */
public class Cart {
    private Logger logger = LoggerFactory.getLogger(Cart.class);

    private List<LineItem> CART = new ArrayList<>();
    private int id;
    private int customerId;


    public Cart(int customerId) {
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LineItem> getCART() {
        return CART;
    }

    public int getSubTotal() {
        int subTotal= 0;
        logger.info("Size of cart: {}", CART.size());
        for (LineItem aCART : CART) {
            subTotal += aCART.getPrice();
        }
        logger.info("Subtotal is: {}", subTotal);
        return subTotal;
    }

    public void setCART(List<LineItem> CART) {
        this.CART = CART;
    }

    public void add(Product product, int quantity) {
        LineItem item = new LineItem(product);
        item.quantity = quantity;
        item.price *= quantity;
        CART.add(item);
        logger.debug("{} {}(s) successfully added to the cart. Price: {}", item.quantity, item.product.name, item.price);
    }
}
