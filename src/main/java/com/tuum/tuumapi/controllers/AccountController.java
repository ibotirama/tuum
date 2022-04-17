package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@Tag(name = "Account endpoint")
@RestController
@RequestMapping("account-service")
public class AccountController {

    private AccountService accountService;

    @Operation(summary = "Creating a new account")
    @ApiResponses({
        @ApiResponse(responseCode = "400", content = @Content, description = "Invalid currency"),
        @ApiResponse(responseCode = "200", content = @Content, description = "Account found"),
    })
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDto accountDto) {
        try {
            AccountResponseDto responseDto = accountService.create(accountDto);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception ex) {
            String msg = "Error trying to create an account. /n" + ex.getMessage();
            log.error(msg);
            return ResponseEntity.badRequest().body("Error trying to create Account");
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
