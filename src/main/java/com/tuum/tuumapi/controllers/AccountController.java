package com.tuum.tuumapi.controllers;

import com.tuum.tuumapi.dtos.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account endpoint")
@RestController
@RequestMapping("account-service")
public class AccountController {

    @Operation(summary = "Creating a new account")
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDTO) {

        return ResponseEntity.ok(accountDTO);
    }

}
