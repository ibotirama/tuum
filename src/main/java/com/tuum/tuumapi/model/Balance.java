package com.tuum.tuumapi.model;

import com.tuum.tuumapi.exceptions.InvalidCurrencyException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;

@Data
@Builder
public class Balance {
    private String accountId;
    private String currencyCode;
    private BigDecimal amount;

    public Balance(String accountId, String currencyCode, BigDecimal amount) {
        validateCurrencyCode(currencyCode);
        this.accountId = accountId;
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    private void validateCurrencyCode(String currencyCode) {
        String[] valids = {"EUR", "SEK", "GBP", "USD"};
        if (!Arrays.asList(valids).contains(currencyCode)) {
            throw new InvalidCurrencyException();
        }
    }
}
