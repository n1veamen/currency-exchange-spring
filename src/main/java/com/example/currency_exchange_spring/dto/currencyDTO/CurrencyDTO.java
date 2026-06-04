package com.example.currency_exchange_spring.dto.currencyDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    @NotNull
    private int id;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String code;

    @NotBlank
    private String fullName;

    @NotBlank
    private String sign;
}
