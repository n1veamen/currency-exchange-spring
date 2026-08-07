package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.ExchangeResponseDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.exception.InvalidDataException;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import com.example.currency_exchange_spring.repository.ExchangeRateRepository;
import com.example.currency_exchange_spring.util.BigDecimalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeResponseDTO exchange(String baseCode, String targetCode, String bridgeCode, BigDecimal amount) {

        if (isSameCurrency(baseCode, targetCode)) {
            throw new InvalidDataException("Source and target currencies are the same");
        }

        Currency baseCurrency = currencyRepository.findByCode(baseCode)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + baseCode));
        Currency targetCurrency = currencyRepository.findByCode(targetCode)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + targetCode));
        Currency bridgeCurrency = currencyRepository.findByCode(bridgeCode)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + targetCode));

        BigDecimal rate = resolveRate(baseCode, targetCode, bridgeCode)
                .orElseThrow(() -> new NotFoundException("Exchange rate not found: " + baseCode + targetCode));

        var response = new ExchangeResponseDTO(
                baseCurrency,
                targetCurrency,
                rate,
                amount,
                BigDecimalUtil.multiply(amount, rate)
        );

        return response;
    }

    private Optional<BigDecimal> findDirectOrReverseRate(String base, String target) {

        Optional<ExchangeRate> directRate = exchangeRateRepository
                .findByBaseCurrency_CodeAndTargetCurrency_Code(base, target);
        if (directRate.isPresent()) {
            return Optional.of(directRate.get().getRate());
        }

        Optional<ExchangeRate> reverseRate = exchangeRateRepository
                .findByBaseCurrency_CodeAndTargetCurrency_Code(target, base);
        if (reverseRate.isPresent()) {
            return Optional.of(BigDecimalUtil.invert(reverseRate.get().getRate()));
        }

        return Optional.empty();
    }

    public Optional<BigDecimal> resolveRate(String baseCode, String targetCode, String bridgeCode) {

        Optional<BigDecimal> directReverse = findDirectOrReverseRate(baseCode, targetCode);
        if (directReverse.isPresent()) {
            return directReverse;
        }

        Optional<BigDecimal> baseToBridge = findDirectOrReverseRate(baseCode, bridgeCode);
        Optional<BigDecimal> bridgeToTarget = findDirectOrReverseRate(bridgeCode, targetCode);

        if (baseToBridge.isPresent() && bridgeToTarget.isPresent()) {
            return Optional.of(
                    BigDecimalUtil.multiply(
                            baseToBridge.get(), bridgeToTarget.get()
                    )
            );
        }

        return Optional.empty();
    }

    private boolean isSameCurrency(String from, String to) {
        return from.equals(to);
    }
}
