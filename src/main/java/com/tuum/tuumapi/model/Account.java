package com.tuum.tuumapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class Account {
    private String id;
    private String customerId;
    private String country;
    private Set<Balance> currencies;
}
