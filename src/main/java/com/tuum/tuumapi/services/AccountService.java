package com.tuum.tuumapi.services;

import com.tuum.tuumapi.conveters.AccountConverter;
import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.mapppers.AccountMapper;
import com.tuum.tuumapi.mapppers.BalanceMapper;
import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountConverter converter;
    private final BalanceMapper balanceMapper;

    @Transactional
    public AccountResponseDto create(AccountRequestDto accountDto){
        Account account = converter.convertToEntity(accountDto);
        accountMapper.create(account);
        createCurrenciesPerAccount(account);
        return converter.convertToResponse(account);
    }

    private void createCurrenciesPerAccount(Account account) {
        account.getCurrencies().forEach(curr -> {
            Balance balance = new Balance(account.getAccountId(), curr.getCurrencyCode(), BigDecimal.ZERO);
            balanceMapper.create(balance);
        });
    }

    public AccountResponseDto findById(String accountId) {
        Account account = accountMapper.findById(accountId);
        return converter.convertToResponse(account);
    }

}
