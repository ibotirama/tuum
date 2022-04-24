package com.tuum.tuumapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.tuumapi.dtos.AccountRequestDto;
import com.tuum.tuumapi.dtos.AccountResponseDto;
import com.tuum.tuumapi.dtos.CurrencyRequestDto;
import com.tuum.tuumapi.dtos.CurrencyResponseDto;
import com.tuum.tuumapi.exceptions.InvalidAccountException;
import com.tuum.tuumapi.exceptions.InvalidCurrencyException;
import com.tuum.tuumapi.services.AccountService;
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
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMybatis
class AccountControllerTest {
    private final CurrencyResponseDto usdResponseDto = CurrencyResponseDto.builder()
        .currencyCode("USD")
        .amount(BigDecimal.ZERO)
        .build();
    private final Set<CurrencyResponseDto> currenciesRespDto = Set.of(usdResponseDto);
    private final AccountResponseDto responseDto = AccountResponseDto.builder()
        .accountId("generated-account-id")
        .customerId("customer-test-id")
        .country("BR")
        .currencies(currenciesRespDto)
        .build();

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAccount() throws Exception {
        //given
        Set<CurrencyRequestDto> currenciesReqDto = Set.of(new CurrencyRequestDto("USD"));
        AccountRequestDto requestDto = AccountRequestDto.builder()
            .customerId("customer-test-id")
            .country("BR")
            .currencies(currenciesReqDto)
            .build();

        given(accountService.create(requestDto)).willReturn(responseDto);

        // when
        mockMvc.perform(post("/account-service")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
            )
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(4))
            .andExpect(jsonPath("accountId").value("generated-account-id"))
            .andExpect(jsonPath("customerId").value("customer-test-id"))
            .andExpect(jsonPath("country").value("BR"))
            .andExpect(jsonPath("$.currencies").isArray())
            .andExpect(jsonPath("$.currencies", hasSize(1)))
            .andExpect(jsonPath("$.currencies.[0].currencyCode").value(usdResponseDto.getCurrencyCode()))
            .andExpect(jsonPath("$.currencies.[0].amount").value(BigDecimal.ZERO));
    }

    @Test
    void shouldFailToCreateAccountWithInvalidCurrency() throws Exception {
        //given
        Set<CurrencyRequestDto> currenciesReqDto = Set.of(new CurrencyRequestDto("BRL"));
        AccountRequestDto requestDto = AccountRequestDto.builder()
            .customerId("customer-test-id")
            .country("BR")
            .currencies(currenciesReqDto)
            .build();

        given(accountService.create(requestDto)).willThrow(InvalidCurrencyException.class);

        // when
        mockMvc.perform(post("/account-service")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
            )
            // then
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFailToCreateInvalidAccount() throws Exception {
        //given
        Set<CurrencyRequestDto> currenciesReqDto = Set.of(new CurrencyRequestDto("BRL"));
        AccountRequestDto requestDto = AccountRequestDto.builder()
            .customerId("customer-test-id")
            .currencies(currenciesReqDto)
            .build();

        given(accountService.create(requestDto)).willThrow(InvalidAccountException.class);

        // when
        mockMvc.perform(post("/account-service")
                .content(objectMapper.writeValueAsString(requestDto))
            )
            // then
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFindAccountById() throws Exception {
        // given
        given(accountService.findById("12345")).willReturn(responseDto);

        // when
        mockMvc.perform(get("/account-service/12345")
                .contentType(MediaType.APPLICATION_JSON)
            )
        //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(4))
            .andExpect(jsonPath("accountId").value("generated-account-id"))
            .andExpect(jsonPath("customerId").value("customer-test-id"))
            .andExpect(jsonPath("country").value("BR"))
            .andExpect(jsonPath("$.currencies").isArray())
            .andExpect(jsonPath("$.currencies", hasSize(1)))
            .andExpect(jsonPath("$.currencies.[0].currencyCode").value(usdResponseDto.getCurrencyCode()))
            .andExpect(jsonPath("$.currencies.[0].amount").value(BigDecimal.ZERO));
    }

    @Test
    void shouldFailToFindAccountByIdAndReturnNotFound() throws Exception {
        // given
        given(accountService.findById("12345")).willReturn(null);

        // when
        mockMvc.perform(get("/account-service/12345")
                .contentType(MediaType.APPLICATION_JSON)
            )
        //then
            .andExpect(status().isNotFound());
    }
}
