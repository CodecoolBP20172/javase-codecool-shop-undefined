package com.codecool.shop.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an order.
 * An order connects a customer and a cart.
 * The instance contains reference to both objects.
 *
 * @since 1.0
 */
public class Order {
    private Logger logger = LoggerFactory.getLogger(Order.class);

    private int id;
    private Customer customer;
    private Cart cart;
    private int customerId = 1;

    /**
     * Constructor of the order class, which sets the customer and cart instance fields.
     */
    public Order(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    /**
     * Constructor of the product class, which sets the customer_id field.
     */
    public Order(int customerId) {
        this.customerId = customerId;
        logger.info("New customer id: {} has been set for order with id: {}", customerId, this.getId());
    }


    public Integer getCustomerId() {
        return customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        logger.info("New id: {} has been set for order", id);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        logger.info("New customer: {} has been set for order with id: {}", customer, this.getId());
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        logger.info("New cart: {} has been set for order with id: {}", cart, this.getId());
    }
}
