package com.tuum.tuumapi.exceptions.handler;

import com.tuum.tuumapi.exceptions.AccountNotFoundException;
import com.tuum.tuumapi.exceptions.InsufficientFoundsException;
import com.tuum.tuumapi.exceptions.InvalidAccountException;
import com.tuum.tuumapi.exceptions.InvalidCurrencyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { AccountNotFoundException.class })
    protected ResponseEntity<Object> handleAccount(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Account not found.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { InsufficientFoundsException.class })
    protected ResponseEntity<Object> handleFounds(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { InvalidCurrencyException.class })
    protected ResponseEntity<Object> handleCurrency(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid currency.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {InvalidAccountException.class })
    protected ResponseEntity<Object> handleInvalidAccount(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid account.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class })
    protected ResponseEntity<Object> handleInvalidArgs(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
