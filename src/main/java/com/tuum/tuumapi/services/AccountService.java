package com.tuum.tuumapi.services;

import com.tuum.tuumapi.conveters.AccountConverter;
import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.CurrencyRequestDto;
import com.tuum.tuumapi.exceptions.AccountNotFoundException;
import com.tuum.tuumapi.mapppers.AccountMapper;
import com.tuum.tuumapi.mapppers.BalanceMapper;
import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountConverter converter;
    private final BalanceMapper balanceMapper;

    public AccountService(AccountMapper accountMapper, AccountConverter converter, BalanceMapper balanceMapper) {
        this.accountMapper = accountMapper;
        this.converter = converter;
        this.balanceMapper = balanceMapper;
    }

    @Transactional
    public AccountResponseDto create(AccountRequestDto accountDto){
        Account account = converter.convertToEntity(accountDto);
        accountMapper.create(account);
        createCurrenciesPerAccount(account.getId(), accountDto.getCurrencies());
        Set<Balance> currencies = balanceMapper.getBalancesBy(account.getId());
        return converter.convertToResponse(account, currencies);
    }

    private void createCurrenciesPerAccount(String accountId, Set<CurrencyRequestDto> currencies) {
        currencies.forEach(curr -> {
            Balance balance = new Balance(accountId, curr.getCurrencyCode(), BigDecimal.ZERO);
            balanceMapper.create(balance);
        });
    }

    public AccountResponseDto findById(String accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null){
            throw new AccountNotFoundException(accountId);
        }
        Set<Balance> currencies = balanceMapper.getBalancesBy(account.getId());
        return converter.convertToResponse(account,currencies);
    }

}
