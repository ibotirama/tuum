package com.tuum.tuumapi.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Data
public class AccountResponseDto {
    private String accountId;
    @NotBlank
    private String customerId;
    @Size(min = 3, max = 3)
    @NotBlank
    private String country;
    private Set<CurrencyResponseDto> currencies;
}
