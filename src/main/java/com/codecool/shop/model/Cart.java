package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<LineItem> CART = new ArrayList<>();
    int id;
    int customerId;


    public Cart(int customerId) {
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LineItem> getCART() {
        return CART;
    }

    public int getSubTotal() {
        int subTotal= 0;
        for (int i = 0; i < CART.size(); i++) {
            subTotal += CART.get(i).getPrice();
        }
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
    }
}
