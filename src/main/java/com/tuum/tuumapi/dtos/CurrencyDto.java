package com.tuum.tuumapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrencyDto {
    @Size(min = 3, max = 3)
    @NotBlank
    private String currencyCode;
    private BigDecimal amount;
}
