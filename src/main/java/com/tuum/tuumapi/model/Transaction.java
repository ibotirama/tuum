package com.tuum.tuumapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Transaction {
    private String id;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionDirection direction;
    private String description;
    private BigDecimal balance;
}
