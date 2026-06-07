package com.example.currency_exchange_spring.dto.exchangeRateDTO;

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
public class CreateExchangeRateDTO {

    @NotBlank
    private String baseCurrencyCode;

    @NotBlank
    private String targetCurrencyCode;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal rate;
}
