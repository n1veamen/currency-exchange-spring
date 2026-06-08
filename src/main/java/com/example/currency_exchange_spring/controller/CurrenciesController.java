package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.request.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.response.CurrencyResponseDTO;
import com.example.currency_exchange_spring.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrenciesController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyResponseDTO>> getAll() {
        return ResponseEntity.ok(currencyService.getAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyResponseDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getByCode(code));
    }

    @PostMapping
    public ResponseEntity<Void> createCurrency(@Valid @RequestBody CreateCurrencyDTO request) {
        currencyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
