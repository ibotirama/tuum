package com.tuum.tuumapi.exceptions;

public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException(String accountId) {
        super(String.format("Invalid currency %s", accountId));
    }
}
