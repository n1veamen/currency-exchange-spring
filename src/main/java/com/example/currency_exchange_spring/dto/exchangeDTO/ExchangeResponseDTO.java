package com.example.currency_exchange_spring.dto.exchangeDTO;

import com.example.currency_exchange_spring.entity.Currency;

import java.math.BigDecimal;

public class ExchangeResponseDTO {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
}
