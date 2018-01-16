package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/** Supplier class which covers supplier related fields and methods.
 * @author      Anikó Barát
 * @version     1.0
 * @since       1.0
 */

public class Supplier extends BaseModel {

    Logger logger = LoggerFactory.getLogger(Supplier.class);


    private ArrayList<Product> products;

    /**
     * Constructor of the product class, which sets the instance fields
     */
    public Supplier(String name, String description) {
        super(name);
        this.products = new ArrayList<>();
        logger.info("New supplier instance created with id: {},", this.getId());

    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        logger.info("New product with id: {} added to supplier: {} with id: {}", product.getId(), this.getName(), this.getId());

    }
    /**
     * Builds a string from the instance variables of the supplier
     * including id, name, description
     *
     * @return String with the information about the supplier instance
     */
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}