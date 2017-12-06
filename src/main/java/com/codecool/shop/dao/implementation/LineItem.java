package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;

public class LineItem {
    public Product product;
    public int quantity = 1;
    public float price;

    LineItem(Product product) {
        this.product = product;
        this.price = product.getDefaultPrice();
    }
}
