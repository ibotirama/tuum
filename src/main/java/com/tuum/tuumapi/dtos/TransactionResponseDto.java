package com.tuum.tuumapi.dtos;

import com.tuum.tuumapi.model.TransactionDirection;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponseDto {
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionDirection direction;
    private String description;
    private BigDecimal balance;
}
