package com.tuum.tuumapi.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CurrencyRequestDto {
    @Size(min = 3, max = 3)
    @NotBlank
    private String currencyCode;

    @JsonCreator
    public CurrencyRequestDto(@Size(min = 3, max = 3) @NotBlank @JsonProperty("currencyCode") String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
