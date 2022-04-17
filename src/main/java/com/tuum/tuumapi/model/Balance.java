package com.tuum.tuumapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Balance {
    private String accountId;
    private String currencyCode;
    private BigDecimal amount;
}
