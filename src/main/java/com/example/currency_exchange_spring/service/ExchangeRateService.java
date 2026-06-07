package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.exception.AlreadyExistsException;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import com.example.currency_exchange_spring.repository.ExchangeRateRepository;
import com.example.currency_exchange_spring.util.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository rateRepository;

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Autowired
    private CurrencyService currencyService;

    public List<ExchangeRate> getAll() {
        return rateRepository.findAll();
    }

    public ExchangeRateResponseDTO getResponseByCurrencyPair(CurrencyPair pair) {

        return exchangeRateMapper.toDTO(getByCurrencyPair(pair)
            .orElseThrow(
                    () -> new NotFoundException(
                        "Exchange rate not found: "
                        + pair.getBaseCurrencyCode()
                        + pair.getTargetCurrencyCode()
                )
            )
        );

    }

    public Optional<ExchangeRate> getByCurrencyPair(CurrencyPair pair) {
        return rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                pair.getBaseCurrencyCode(),
                pair.getTargetCurrencyCode()
        );
    }

    public ExchangeRateResponseDTO update(CurrencyPair currencyPair, UpdateExchangeRateDTO updateExchangeRateDTO) {

        String baseCurrencyCode = currencyPair.getBaseCurrencyCode();
        String targetCurrencyCode = currencyPair.getTargetCurrencyCode();

        ExchangeRate exchangeRate = rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                baseCurrencyCode,
                targetCurrencyCode
        ).orElseThrow( () -> new NotFoundException("Missing required field: " + baseCurrencyCode + targetCurrencyCode) );

        exchangeRate.setRate(updateExchangeRateDTO.getRate());

        ExchangeRate saved = rateRepository.save( exchangeRate );

        return exchangeRateMapper.toDTO(saved);

    }

    public ExchangeRate create(CreateExchangeRateDTO dto) {

        Currency baseCurrency = currencyService.getByCode(dto.getBaseCurrencyCode());
        Currency targetCurrency = currencyService.getByCode(dto.getTargetCurrencyCode());

        checkPairNotExists(dto.getBaseCurrencyCode(), dto.getTargetCurrencyCode());

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(dto.getRate());

        return rateRepository.save(exchangeRate);

    }

    private void checkPairNotExists(String baseCode, String targetCode) {
        if (rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                baseCode,
                targetCode
        ).isPresent() ) {
            throw new AlreadyExistsException("Exchange rate already exists: " + baseCode + targetCode);
        }
    }

}
