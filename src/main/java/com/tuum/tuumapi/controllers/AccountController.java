package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.dtos.AccountDto;
import com.tuum.tuumapi.dtos.CurrencyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@Tag(name = "Account endpoint")
@RestController
@RequestMapping("account-service")
public class AccountController {

    @Operation(summary = "Creating a new account")
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDTO) {

        return ResponseEntity.ok(accountDTO);
    }

    @Operation(summary = "Find the account based on the AccountID")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable String accountId) {
        AccountDto accountDto = AccountDto.builder()
            .accountId("MYACC01")
            .country("EE")
            .customerId(1L)
            .currencies(Set.of(new CurrencyDto("EUR", BigDecimal.valueOf(100.0))))
            .build();
        return ResponseEntity.ok(accountDto);
    }

}
