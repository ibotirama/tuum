package com.tuum.tuumapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionRequestDto {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
}
