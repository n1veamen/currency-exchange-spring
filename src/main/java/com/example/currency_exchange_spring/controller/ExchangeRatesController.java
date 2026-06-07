package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.service.ExchangeRateService;
import com.example.currency_exchange_spring.util.CurrencyPair;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchangeRates")
public class ExchangeRatesController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(exchangeRateService.getAll().stream( ).map(exchangeRateMapper::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<?> createExchangeRate(@Valid @RequestBody CreateExchangeRateDTO createExchangeRateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                exchangeRateMapper.toDTO(
                        exchangeRateService.create( createExchangeRateDTO )
                )
        );
    }


}
