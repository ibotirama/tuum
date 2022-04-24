package com.tuum.tuumapi.conveters;

import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.model.Transaction;
import com.tuum.tuumapi.model.TransactionDirection;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionConverter {

    public Transaction convertToEntity(TransactionRequestDto transactionDto) {
        TransactionDirection direction = TransactionDirection.valueOf(transactionDto.getDirection());
        return Transaction.builder()
            .id(UUID.randomUUID().toString())
            .accountId(transactionDto.getAccountId())
            .amount(transactionDto.getAmount())
            .currency(transactionDto.getCurrency())
            .direction(direction)
            .description(transactionDto.getDescription())
            .build();
    }

    public TransactionResponseDto convertToDto(Transaction transaction) {
        return TransactionResponseDto.builder()
            .transactionId(transaction.getId())
            .accountId(transaction.getAccountId())
            .amount(transaction.getAmount())
            .currency(transaction.getCurrency())
            .direction(transaction.getDirection())
            .description(transaction.getDescription())
            .balance(transaction.getBalance())
            .build();
    }
}
