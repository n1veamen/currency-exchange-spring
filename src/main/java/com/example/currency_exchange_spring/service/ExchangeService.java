package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.exchangeDTO.ExchangeResponseDTO;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRateRepository rateRepository;

    @Autowired
    private CurrencyService currencyService;

    public ExchangeResponseDTO exchange(String from, String to, BigDecimal amount) {
        from = from.toUpperCase();
        to = to.toUpperCase();

        BigDecimal effectiveRate = findEffectiveRate(from, to);
        BigDecimal convertedAmount = amount.multiply(effectiveRate);

        return new ExchangeResponseDTO(
                currencyService.getByCode(from),
                currencyService.getByCode(to),
                effectiveRate,
                amount,
                convertedAmount
        );
    }

    private BigDecimal findEffectiveRate(String from, String to) {
        Optional<ExchangeRate> direct = rateRepository
                .findByBaseCurrencyCodeAndTargetCurrencyCode(from, to);

        if (direct.isPresent()) {
            return direct.get().getRate();
        }

        Optional<ExchangeRate> reverse = rateRepository
                .findByBaseCurrencyCodeAndTargetCurrencyCode(to, from);

        if (reverse.isPresent()) {
            return BigDecimal.ONE.divide(reverse.get().getRate(), 10, RoundingMode.HALF_UP);
        }

        List<ExchangeRate> firstLegRates = rateRepository.findByBaseCurrencyCode(from);

        for (ExchangeRate firstLeg : firstLegRates) {
            String intermediate = firstLeg.getTargetCurrency().getCode();
            BigDecimal rate1 = firstLeg.getRate();

            Optional<ExchangeRate> secondDirect = rateRepository
                    .findByBaseCurrencyCodeAndTargetCurrencyCode(intermediate, to);

            if (secondDirect.isPresent()) {
                return rate1.multiply(secondDirect.get().getRate());
            }

            Optional<ExchangeRate> secondReverse = rateRepository
                    .findByBaseCurrencyCodeAndTargetCurrencyCode(to, intermediate);

            if (secondReverse.isPresent()) {
                BigDecimal rate2 = BigDecimal.ONE.divide(
                        secondReverse.get().getRate(), 10, RoundingMode.HALF_UP
                );
                return rate1.multiply(rate2);
            }
        }

        throw new NotFoundException("Exchange rate not found for: " + from + " -> " + to);
    }

}
