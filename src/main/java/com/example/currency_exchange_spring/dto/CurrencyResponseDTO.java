package com.example.currency_exchange_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDTO {

    private int id;

    private String code;

    private String fullName;

    private String sign;

}
