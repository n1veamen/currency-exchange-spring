package com.example.currency_exchange_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCurrencyDTO {

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String code;

    @NotBlank
    private String fullName;

    @NotBlank
    private String sign;

}
