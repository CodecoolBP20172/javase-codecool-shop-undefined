package com.codecool.shop.exception;

public class DaoConnectionException extends DaoException {
    // Parameterless Constructor
    public DaoConnectionException() {}

    // Constructor that accepts a message
    public DaoConnectionException(String message)
    {
        super(message);
    }
}
