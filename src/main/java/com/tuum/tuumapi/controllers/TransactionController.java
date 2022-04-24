package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.conveters.TransactionConverter;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.model.Transaction;
import com.tuum.tuumapi.model.TransactionDirection;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Tag(name = "Transaction endpoint")
@RestController
@RequestMapping("transaction-service")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionConverter converter;

    @Operation(summary = "Creating a new transaction")
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid TransactionRequestDto transactionDto) {
        try {
            TransactionResponseDto responseDto = transactionService.create(transactionDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Find the transactions based on the AccountId")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountId) {
//        try {
            List<TransactionResponseDto> transactions = transactionService.findByAccountId(accountId);
            return ResponseEntity.ok(transactions);
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
    }
}
