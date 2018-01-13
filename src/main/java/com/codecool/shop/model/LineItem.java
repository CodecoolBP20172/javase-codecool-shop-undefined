package com.codecool.shop.model;

public class LineItem {
    public Product product;
    public int quantity;
    public double price;

    LineItem(Product product) {
        this.product = product;
        this.price = product.getDefaultPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }
}
