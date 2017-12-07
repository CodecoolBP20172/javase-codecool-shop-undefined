package com.codecool.shop.model;

import com.codecool.shop.model.Product;

public class LineItem {
    public Product product;
    public int quantity;
    public float price;

    LineItem(Product product) {
        this.product = product;
        this.price = product.getDefaultPrice();
    }
}
