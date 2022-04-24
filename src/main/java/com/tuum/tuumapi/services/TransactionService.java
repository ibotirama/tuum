package com.tuum.tuumapi.services;

import com.tuum.tuumapi.conveters.TransactionConverter;
import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.exceptions.InsufficientFoundsException;
import com.tuum.tuumapi.exceptions.InvalidAccountException;
import com.tuum.tuumapi.mapppers.BalanceMapper;
import com.tuum.tuumapi.mapppers.TransactionMapper;
import com.tuum.tuumapi.model.Transaction;
import com.tuum.tuumapi.model.TransactionDirection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final BalanceMapper balanceMapper;
    private final TransactionConverter converter;

    public TransactionService(TransactionMapper transactionMapper, BalanceMapper balanceMapper, TransactionConverter converter) {
        this.transactionMapper = transactionMapper;
        this.balanceMapper = balanceMapper;
        this.converter = converter;
    }

    @Transactional
    public TransactionResponseDto create(TransactionRequestDto transactionDto) {
        Transaction transaction = converter.convertToEntity(transactionDto);
        BigDecimal balance = balanceMapper.getBalanceBy(transaction.getAccountId(), transaction.getCurrency());
        BigDecimal newValue = transaction.getDirection() == TransactionDirection.IN ? balance.add(transaction.getAmount()) : balance.subtract(transaction.getAmount());
        transaction.setBalance(newValue);
        if (newValue.compareTo(BigDecimal.ZERO) < 0){
            throw new InsufficientFoundsException(transaction.getId());
        }
        balanceMapper.updateBalance(transaction.getAccountId(), transaction.getCurrency(), newValue);
        transactionMapper.create(transaction);
        return converter.convertToDto(transaction);
    }

    public List<TransactionResponseDto> findByAccountId(String accountId) {
        List<Transaction> transactions = transactionMapper.findByAccountId(accountId);
       if (transactions == null){
            throw new InvalidAccountException(String.format("Invalid account %s", accountId));
        }
        return transactions.stream().map(converter::convertToDto).collect(Collectors.toList());
    }
}
