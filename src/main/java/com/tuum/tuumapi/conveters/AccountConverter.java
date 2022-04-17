package com.tuum.tuumapi.conveters;

import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.CurrencyResponseDto;
import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountConverter {

    public Account convertToEntity(AccountRequestDto accountDto) {
        Set<Balance> currencySet = accountDto.getCurrencies().stream().map(curr ->
                Balance.builder()
                    .currencyCode(curr.getCurrencyCode())
                    .amount(BigDecimal.ZERO)
                    .build())
            .collect(Collectors.toSet());
        return Account.builder()
            .accountId(UUID.randomUUID().toString())
            .accountNumber(accountDto.getAccountNumber())
            .customerId(accountDto.getCustomerId())
            .country(accountDto.getCountry())
            .currencies(currencySet)
            .build();
    }

    public AccountResponseDto convertToResponse(Account account) {
        Set<CurrencyResponseDto> currencySet = account.getCurrencies().stream().map(curr ->
                CurrencyResponseDto.builder()
                    .currencyCode(curr.getCurrencyCode())
                    .amount(curr.getAmount())
                    .build())
            .collect(Collectors.toSet());
        return AccountResponseDto.builder()
            .accountNumber(account.getAccountNumber())
            .customerId(account.getCustomerId())
            .country(account.getCountry())
            .currencies(currencySet)
            .build();
    }
}
