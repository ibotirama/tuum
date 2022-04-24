package com.tuum.tuumapi.exceptions;

public class InsufficientFoundsException extends RuntimeException{
    public InsufficientFoundsException(String transactionId) {
        super(String.format("Insuficient founds for this transaction: %s", transactionId));
    }
}
