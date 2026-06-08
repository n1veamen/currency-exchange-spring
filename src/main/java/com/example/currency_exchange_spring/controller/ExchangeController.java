package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.response.ExchangeResponseDTO;
import com.example.currency_exchange_spring.service.ExchangeService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping
    public ResponseEntity<ExchangeResponseDTO> getExchange(
            @RequestParam @Pattern(regexp = "[A-Z]{3}") String baseCode,
            @RequestParam @Pattern(regexp = "[A-Z]{3}") String targetCode,
            @RequestParam @Positive BigDecimal amount
    ) {
        return ResponseEntity.ok(exchangeService.exchange(baseCode, targetCode, amount));
    }

}
