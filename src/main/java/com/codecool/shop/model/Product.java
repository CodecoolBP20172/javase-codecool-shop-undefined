package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Currency;

/** Product class which covers product related fields and methods.
 * @author      Anikó Barát
 * @version     1.0
 * @since       1.0
 */

public class Product extends BaseModel {

    Logger logger = LoggerFactory.getLogger(Product.class);


    private double defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;

    /**
     * Constructor of the product class, which sets the instance fields
     */
    public Product(String name, double defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
        logger.info("New product instance created with id: {},", this.getId());
    }



    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
        logger.info("New default price: {} has been set for product with id: {}", defaultPrice, this.getId());
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
        logger.info("New default currency: {} has been set for product with id: {}", defaultCurrency, this.getId());

    }

    public String getPriceWithoutCurrency() { return String.valueOf(this.defaultPrice);}

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(double price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
        logger.info("New price: {} {} has been set for product with id: {}", price, currency, this.getId());

    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
        logger.info("New product category: {} has been set for product with id: {}", productCategory, this.getId());

    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
        logger.info("New supplier: {} has been set for product with id: {}", supplier, this.getId());

    }

    /**
     * Builds a string from the instance variables of the product
     * including id, name, default price, default currency, product category, supplier.
     *
     * @return String with the information about the product instance
     */
    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }
}
