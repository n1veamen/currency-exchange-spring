package com.example.currency_exchange_spring.controller;

import com.example.currency_exchange_spring.dto.request.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.request.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.response.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.service.ExchangeRateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
@RequiredArgsConstructor
@Validated
public class ExchangeRatesController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponseDTO>> getAll() {
        return ResponseEntity.ok(exchangeRateService.getAll());
    }

    @GetMapping("/{codePair}")
    public ResponseEntity<ExchangeRateResponseDTO> getByCodePair(@Pattern(regexp = "[A-Z]{6}") @PathVariable String codePair) {
        return ResponseEntity.ok(exchangeRateService.getByCodePair(codePair));
    }

    @PostMapping
    public ResponseEntity<Void> createExchangeRate(@Valid @RequestBody CreateExchangeRateDTO request) {
        exchangeRateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{codePair}")
    public ResponseEntity<ExchangeRateResponseDTO> updateByCode(
            @PathVariable String codePair,
            @Valid @RequestBody UpdateExchangeRateDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                exchangeRateService.update(codePair, request)
        );
    }


}
