package com.tuum.tuumapi.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountId) {
        super(String.format("Account not found: %s", accountId));
    }
}
