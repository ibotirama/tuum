package com.tuum.tuumapi.conveters;

import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.CurrencyResponseDto;
import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountConverter {

    public Account convertToEntity(AccountRequestDto accountDto) {
        String accountId = UUID.randomUUID().toString();
        return Account.builder()
            .id(accountId)
            .customerId(accountDto.getCustomerId())
            .country(accountDto.getCountry())
            .build();
    }

    public AccountResponseDto convertToResponse(Account account, Set<Balance> currencies) {
        Set<CurrencyResponseDto> currencySet = currencies.stream().map(curr ->
                CurrencyResponseDto.builder()
                    .currencyCode(curr.getCurrencyCode())
                    .amount(curr.getAmount())
                    .build())
            .collect(Collectors.toSet());
        return AccountResponseDto.builder()
             .accountId(account.getId())
            .customerId(account.getCustomerId())
            .country(account.getCountry())
            .currencies(currencySet)
            .build();
    }
}
