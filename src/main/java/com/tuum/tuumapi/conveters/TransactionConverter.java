package com.tuum.tuumapi.conveters;

import com.tuum.tuumapi.dtos.*;
import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import com.tuum.tuumapi.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionConverter {

    public Transaction convertToEntity(TransactionRequestDto transactionDto) {
        return Transaction.builder()
            .transactionId(UUID.randomUUID().toString())
            .accountId(transactionDto.getAccountId())
            .amount(transactionDto.getAmount())
            .currency(transactionDto.getCurrency())
            .direction(transactionDto.getDirection())
            .description(transactionDto.getDescription())
            .build();
    }

    public TransactionResponseDto convertToDto(Transaction transaction) {
        return TransactionResponseDto.builder()
            .transactionId(transaction.getTransactionId())
            .accountId(transaction.getAccountId())
            .amount(transaction.getAmount())
            .currency(transaction.getCurrency())
            .direction(transaction.getDirection())
            .description(transaction.getDescription())
            .balance(transaction.getBalance())
            .build();
    }
}
