package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.service.ExchangeService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @GetMapping
    public ResponseEntity<?> getExchange(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok( exchangeService.exchange(from, to, amount) );
    }

}
