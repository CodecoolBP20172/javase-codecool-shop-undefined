package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customer {

    private Logger logger = LoggerFactory.getLogger(BaseModel.class);
    protected int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String billCountry;
    private String billCity;
    private Integer billZip;
    private String billAddress;

    private String shipCountry;
    private String shipCity;
    private Integer shipZip;
    private String shipAddress;

    public Customer(
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            String billCountry,
            String billCity,
            Integer billZip,
            String billAddress,
            String shipCountry,
            String shipCity,
            Integer shipZip,
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
        logger.info("Customer instance successfully created with name: {} {}", firstName, lastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public String getBillCity() {
        return billCity;
    }

    public Integer getBillZip() {
        return billZip;
    }

    public Integer getShipZip() {
        return shipZip;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public String getShipCity() {
        return shipCity;
    }

    public String getShipAddress() {
        return shipAddress;
    }
}
