package com.example.currency_exchange_spring.dto;

import com.example.currency_exchange_spring.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponseDTO {

    private int id;

    private CurrencyResponseDTO baseCurrency;

    private CurrencyResponseDTO targetCurrency;

    private BigDecimal rate;

}
