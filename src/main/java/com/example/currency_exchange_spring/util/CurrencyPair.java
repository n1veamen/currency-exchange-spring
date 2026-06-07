package com.example.currency_exchange_spring.util;

import com.example.currency_exchange_spring.exception.InvalidDataException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyPair {

    private String baseCurrencyCode;
    private String targetCurrencyCode;

    public CurrencyPair(String codePair) {

        if (codePair == null || codePair.length() != 6) {
            throw new InvalidDataException("Invalid currency pair: " + codePair);
        }

        if (!codePair.matches("[A-Za-z]{6}")) {
            throw new InvalidDataException("Currency pair must contain only letters: " + codePair);
        }

        this.baseCurrencyCode = codePair.substring(0, 3).toUpperCase();
        this.targetCurrencyCode = codePair.substring(3, 6).toUpperCase();
    }

    public CurrencyPair(String from, String to) {

        this.baseCurrencyCode = from;
        this.targetCurrencyCode = to;

    }

    public String getCurrencyPair() {
        return baseCurrencyCode + targetCurrencyCode;
    }
}
