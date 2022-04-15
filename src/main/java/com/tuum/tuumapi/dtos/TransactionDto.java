package com.tuum.tuumapi.dtos;

import com.tuum.tuumapi.domain.TransactionDirection;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionDto {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionDirection direction;
    private String description;

}
