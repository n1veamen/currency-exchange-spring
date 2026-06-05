package com.example.currency_exchange_spring.util;

import lombok.Getter;

@Getter
public class CurrencyPair {

    private final String baseCurrencyCode;
    private final String targetCurrencyCode;

    public CurrencyPair(String codePair) {
        this.baseCurrencyCode = codePair.substring(0, 3).toUpperCase();
        this.targetCurrencyCode = codePair.substring(3, 6).toUpperCase();
    }
}
