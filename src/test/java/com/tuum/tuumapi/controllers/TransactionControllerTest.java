package com.tuum.tuumapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.TransactionRequestDto;
import com.tuum.tuumapi.dtos.TransactionResponseDto;
import com.tuum.tuumapi.exceptions.InsufficientFoundsException;
import com.tuum.tuumapi.model.TransactionDirection;
import com.tuum.tuumapi.services.AccountService;
import com.tuum.tuumapi.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@AutoConfigureMybatis
class TransactionControllerTest {

    private final TransactionResponseDto responseDto = TransactionResponseDto.builder()
        .transactionId("transaction-id")
        .accountId("account-id")
        .currency("USD")
        .balance(BigDecimal.TEN)
        .direction(TransactionDirection.IN)
        .description("Test")
        .build();
    private final AccountResponseDto accountResponseDto = AccountResponseDto.builder()
        .accountId("account-id-test")
        .accountId("account-id-test")
        .country("EUA").
        build();

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreaateTransation() throws Exception {
        TransactionRequestDto requestDto = TransactionRequestDto.builder()
            .accountId("account-id-test")
            .currency("USD")
            .amount(BigDecimal.TEN)
            .direction(TransactionDirection.IN.getLabel())
            .description("Test")
            .build();

        given(accountService.findById("account-id-test")).willReturn(accountResponseDto);
        given(transactionService.create(requestDto)).willReturn(responseDto);

        mockMvc.perform(
                post("/transaction-service")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(requestDto))
            )
            .andExpect(jsonPath("transactionId").value("transaction-id"))
            .andExpect(jsonPath("accountId").value("account-id"))
            .andExpect(jsonPath("currency").value("USD"))
            .andExpect(jsonPath("balance").value("10"))
            .andExpect(jsonPath("direction").value("IN"))
            .andExpect(jsonPath("description").value("Test"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldFailToCreateTransactionOutInWithInsufficientFounds() throws Exception {
        TransactionRequestDto requestDto = TransactionRequestDto.builder()
            .accountId("account-id-test")
            .currency("USD")
            .amount(BigDecimal.TEN)
            .direction(TransactionDirection.IN.getLabel())
            .description("Test")
            .build();

        given(transactionService.create(requestDto)).willThrow(InsufficientFoundsException.class);

        mockMvc.perform(
                post("/transaction-service")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(requestDto))
            )
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFailToCreateTransactionWithInvalidDirection() throws Exception {
        TransactionRequestDto requestDto = TransactionRequestDto.builder()
            .accountId("account-id-test")
            .currency("USD")
            .amount(BigDecimal.TEN)
            .direction("TEST")
            .description("Test")
            .build();

        given(transactionService.create(requestDto)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(
                post("/transaction-service")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(requestDto))
            )
            .andExpect(status().is4xxClientError());
    }
}
