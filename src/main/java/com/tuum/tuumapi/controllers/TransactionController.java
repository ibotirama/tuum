package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.exceptions.InvalidAccountException;
import com.tuum.tuumapi.exceptions.InvalidCurrencyException;
import com.tuum.tuumapi.model.TransactionDirection;
import com.tuum.tuumapi.services.AccountService;
import com.tuum.tuumapi.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Tag(name = "Transaction endpoint")
@RestController
@RequestMapping("transaction-service")
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Operation(summary = "Creating a new transaction")
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid TransactionRequestDto transactionDto) {
        validate(transactionDto);
        TransactionResponseDto responseDto = transactionService.create(transactionDto);
        return ResponseEntity.ok(responseDto);
    }

    private void validate(TransactionRequestDto transactionDto) {
        AccountResponseDto accountDto = accountService.findById(transactionDto.getAccountId());
        if (accountDto == null){
            throw new InvalidAccountException("Invalid account");
        }

        if (transactionDto.getAccountId().isBlank()){
            throw new InvalidAccountException("Account missing");
        }

        TransactionDirection direction = TransactionDirection.findByName(transactionDto.getDirection());
        if (direction == null){
            throw new IllegalArgumentException("Invalid direction");

        }

        if (transactionDto.getDescription().isBlank()){
            throw new IllegalArgumentException("Description is missing");
        }

        String[] valids = {"EUR", "SEK", "GBP", "USD"};
        if (!Arrays.asList(valids).contains(transactionDto.getCurrency())) {
            throw new InvalidCurrencyException(transactionDto.getCurrency());
        }

        if(transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Invalid amount");
        }

    }

    @Operation(summary = "Find the transactions based on the AccountId")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountId) {
        try {
            List<TransactionResponseDto> transactions = transactionService.findByAccountId(accountId);
            return ResponseEntity.ok(transactions);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
