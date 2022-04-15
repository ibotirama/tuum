package com.tuum.tuumapi.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Builder
@Data
public class AccountDto {
    @NotBlank
    private Long customerID;
    @Max(value = 2)
    private String country;
    private Set<CurrencyDto> currencies;
}
