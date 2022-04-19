package com.tuum.tuumapi.exceptions;

public class InvalidAccountException extends RuntimeException{
    public InvalidAccountException(String accountId) {
        super(String.format("Invalid account %s", accountId));
    }
}
