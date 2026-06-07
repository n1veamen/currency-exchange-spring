package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.service.ExchangeRateService;
import com.example.currency_exchange_spring.util.CurrencyPair;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;



    @GetMapping("/{codePair}")
    public ResponseEntity<?> getByCodePair(@PathVariable String codePair) {

        return ResponseEntity.ok(
                exchangeRateService.getResponseByCurrencyPair(
                        new CurrencyPair(codePair)
                )
        );

    }

    @PatchMapping("/{codePair}")
    public ResponseEntity<?> updateByCode(
            @PathVariable String codePair,
            @Valid @RequestBody UpdateExchangeRateDTO dto
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                exchangeRateService.update(
                        new CurrencyPair(codePair),
                        dto
                )
        );

    }
}
