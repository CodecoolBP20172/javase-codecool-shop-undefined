package com.codecool.shop.exception;

public class DaoException extends Exception {
    // Parameterless Constructor
    public DaoException() {}

    // Constructor that accepts a message
    public DaoException(String message)
    {
        super(message);
    }
}
