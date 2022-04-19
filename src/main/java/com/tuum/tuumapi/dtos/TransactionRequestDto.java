package com.tuum.tuumapi.dtos;

import com.tuum.tuumapi.model.TransactionDirection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionDirection direction;
    private String description;
}
