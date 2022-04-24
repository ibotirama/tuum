package com.tuum.tuumapi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Balance {
    private String accountId;
    private String currencyCode;
    private BigDecimal amount;

    public Balance(String accountId, String currencyCode, BigDecimal amount) {
        this.accountId = accountId;
        this.currencyCode = currencyCode;
        this.amount = amount;
    }
}
