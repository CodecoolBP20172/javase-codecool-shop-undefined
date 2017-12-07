package com.codecool.shop.dao.customer;

public class Customer {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;

    protected String billCountry;
    protected String billCity;
    protected String billZip;
    protected String billAddress;

    protected String shipCountry;
    protected String shipCity;
    protected String shipZip;
    protected String shipAddress;

    public Customer(
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            String billCountry,
            String billCity,
            String billZip,
            String billAddress,
            String shipCountry,
            String shipCity,
            String shipZip,
            String shipAddress
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.billCountry = billCountry;
        this.billCity = billCity;
        this.billZip = billZip;
        this.billAddress = billAddress;
        this.shipCountry = shipCountry;
        this.shipCity = shipCity;
        this.shipZip = shipZip;
        this.shipAddress = shipAddress;
    }

}
