package com.tuum.tuumapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionDirection direction;
    private String description;
    private BigDecimal balance;
}
