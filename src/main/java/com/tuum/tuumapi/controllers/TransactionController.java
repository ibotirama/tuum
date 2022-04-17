package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.model.TransactionDirection;
import com.tuum.tuumapi.dtos.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Tag(name = "Transaction endpoint")
@RestController
@RequestMapping("transaction-service")
public class TransactionController {

    @Operation(summary = "Creating a new transaction")
    @PostMapping
    public ResponseEntity<TransactionDto> createAccount(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionDto);
    }

    @Operation(summary = "Find the transaction based on the TransactionID")
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId) {
        TransactionDto transactionDto = TransactionDto.builder()
            .accountId("MYACC01")
            .amount(BigDecimal.valueOf(1000))
            .currency("USD")
            .direction(TransactionDirection.IN)
            .description("Withdraw my rental fee.")
            .build();
        return ResponseEntity.ok(transactionDto);
    }
}
