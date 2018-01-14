package com.codecool.shop.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a lineitem.
 * A lineitem contains a products id and the quantity put in a cart.
 * The instance contains reference to product_id and cart_id.
 *
 * @since 1.0
 */
public class LineItem {
    private Logger logger = LoggerFactory.getLogger(LineItem.class);

    public Product product;
    public int quantity;
    public double price;

    /**
     * Constructor of the LineItem class, which sets the product instance field and its price field.
     */
    LineItem(Product product) {
        this.product = product;
        this.price = product.getDefaultPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        logger.info("New product: {} (id: {}) has been set for LineItem", product, product.getId());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        logger.info("New quantity: {} has been set for LineItem with product: {} (id: {})",
                    quantity, this.getProduct(), this.getProduct().getId());
    }

    public double getPrice() {
        return price;
    }
}
