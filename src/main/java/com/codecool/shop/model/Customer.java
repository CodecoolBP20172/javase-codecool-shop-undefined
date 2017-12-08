package com.codecool.shop.model;

public class Customer {

    protected int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String billCountry;
    private String billCity;
    private String billZip;
    private String billAddress;

    private String shipCountry;
    private String shipCity;
    private String shipZip;
    private String shipAddress;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }

    public String getBillCity() {
        return billCity;
    }

    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillZip() {
        return billZip;
    }

    public void setBillZip(String billZip) {
        this.billZip = billZip;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
}
