package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.service.ExchangeRateService;
import com.example.currency_exchange_spring.util.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {
    @Autowired
    ExchangeRateService exchangeRateService;

    @GetMapping("/{codePair}")
    public ResponseEntity<?> getByCodePair(@PathVariable String codePair) {

        return ResponseEntity.ok(
                exchangeRateService.getByCurrencyPair(
                        new CurrencyPair(codePair)
                )
        );

    }

    @PatchMapping("/{codePair}")
    public ResponseEntity<?> updateByCode(@PathVariable String codePair, UpdateExchangeRateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                exchangeRateService.update(
                        new CurrencyPair(codePair),
                        dto
                )
        );

    }
}
