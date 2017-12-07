package com.codecool.shop.model;

public class Order {

    private int id;
    private Customer customer;
    //private Cart cart;

    public Order(Customer customer) {
        this.customer = customer;
        //this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
