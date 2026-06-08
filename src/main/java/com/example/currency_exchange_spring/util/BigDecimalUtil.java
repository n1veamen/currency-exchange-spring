package com.example.currency_exchange_spring.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BigDecimalUtil {

    private static final int SCALE = 10;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    // Умножение
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(SCALE, RoundingMode.HALF_UP);
    }

    // Деление
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b, SCALE, ROUNDING);
    }

    // Обратный курс (1 / rate)
    public static BigDecimal invert(BigDecimal rate) {
        return BigDecimal.ONE.divide(rate, SCALE, ROUNDING);
    }

    // Сложение
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    // Вычитание
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }
}
