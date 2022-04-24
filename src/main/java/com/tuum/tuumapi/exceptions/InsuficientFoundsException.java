package com.tuum.tuumapi.exceptions;

public class InsuficientFoundsException extends RuntimeException{
    public InsuficientFoundsException(String transactionId) {
        super(String.format("Insuficient founds for this transaction: %s", transactionId));
    }
}
