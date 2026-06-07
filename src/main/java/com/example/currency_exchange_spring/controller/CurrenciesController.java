package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.currencyDTO.CreateCurrencyDTO;
import com.example.currency_exchange_spring.mapper.CurrencyMapper;
import com.example.currency_exchange_spring.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    CurrencyMapper currencyMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(currencyService.getAll().stream( ).map(currencyMapper::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<?> createCurrency(@Valid @RequestBody CreateCurrencyDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                currencyMapper.toDTO( currencyService.createCurrency(request) )
        );

    }

}
