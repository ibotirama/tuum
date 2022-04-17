package com.tuum.tuumapi.exceptions;

public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException() {
        super("Invalid currency");
    }
}
