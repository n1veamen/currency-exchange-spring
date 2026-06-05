package com.example.currency_exchange_spring.dto.exchangeRateDTO;

import com.example.currency_exchange_spring.entity.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponseDTO {

    private int id;

    private Currency baseCurrency;

    private Currency targetCurrency;

    private BigDecimal rate;

}
