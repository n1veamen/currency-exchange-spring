package com.example.currency_exchange_spring.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyPairParser {

    public static String getBaseCode(String codePair) {
        return codePair.substring(0, 3);
    }

    public static String getTargetCode(String codePair) {
        return codePair.substring(3, 6);
    }

}
