package com.tuum.tuumapi.services;

import com.tuum.tuumapi.conveters.TransactionConverter;
import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.exceptions.InvalidAccountException;
import com.tuum.tuumapi.mapppers.AccountMapper;
import com.tuum.tuumapi.mapppers.BalanceMapper;
import com.tuum.tuumapi.mapppers.TransactionMapper;
import com.tuum.tuumapi.model.Transaction;
import com.tuum.tuumapi.model.TransactionDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final BalanceMapper balanceMapper;
    private final TransactionConverter converter;

    @Transactional
    public TransactionResponseDto create(TransactionRequestDto transactionDto) {
        BigDecimal balance = balanceMapper.getBalanceBy(transactionDto.getAccountId(), transactionDto.getCurrency());
        Transaction transaction = converter.convertToEntity(transactionDto);
        transaction.setBalance(balance);
        BigDecimal newValue = transaction.getDirection() == TransactionDirection.IN ? balance.add(transaction.getAmount()) : balance.subtract(transaction.getAmount());
        balanceMapper.updateBalance(transaction.getAccountId(), transaction.getCurrency(), newValue);
        transactionMapper.create(transaction);
        return converter.convertToDto(transaction);
    }

    public List<TransactionResponseDto> findByAccountId(String accountId) {
        List<Transaction> transactions = transactionMapper.findByAccountId(accountId);
        if (transactions == null){
            throw new InvalidAccountException(accountId);
        }
        return transactions.stream().map(converter::convertToDto).collect(Collectors.toList());
    }
}
