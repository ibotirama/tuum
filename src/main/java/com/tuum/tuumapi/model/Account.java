package com.tuum.tuumapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Account {
    private String id;
    private String customerId;
    private String country;
}
