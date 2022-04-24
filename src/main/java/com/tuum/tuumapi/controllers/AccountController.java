package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.CurrencyRequestDto;
import com.tuum.tuumapi.exceptions.InvalidCurrencyException;
import com.tuum.tuumapi.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@Tag(name = "Account endpoint")
@RestController
@RequestMapping("account-service")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Creating a new account")
    @ApiResponses({
        @ApiResponse(responseCode = "400", content = @Content, description = "Invalid currency"),
        @ApiResponse(responseCode = "200", content = @Content, description = "Account found"),
    })
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDto accountDto) {
        try {
            validade(accountDto);
            AccountResponseDto responseDto = accountService.create(accountDto);
            return ResponseEntity.ok(responseDto);
        } catch (InvalidCurrencyException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Error trying to create Account");
        }
    }

    private void validade(AccountRequestDto accountDto) {
        String[] valids = {"EUR", "SEK", "GBP", "USD"};
        for(CurrencyRequestDto dto: accountDto.getCurrencies()){
            if (!Arrays.asList(valids).contains(dto.getCurrencyCode())) {
                throw new InvalidCurrencyException(dto.getCurrencyCode());
            }
        }
    }

    @Operation(summary = "Find the account based on the AccountID")
    @ApiResponses({
        @ApiResponse(responseCode = "404", content = @Content, description = "Account not found"),
        @ApiResponse(responseCode = "200", content = @Content, description = "Account found"),
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable String accountId) {
        AccountResponseDto responseDto = accountService.findById(accountId);
        if (responseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDto);
    }

}
