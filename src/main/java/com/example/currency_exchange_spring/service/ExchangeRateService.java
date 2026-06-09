package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.request.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.request.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.response.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.exception.AlreadyExistsException;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import com.example.currency_exchange_spring.repository.ExchangeRateRepository;
import com.example.currency_exchange_spring.util.CurrencyPairParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;
    private final CurrencyRepository currencyRepository;

    public List<ExchangeRateResponseDTO> getAll() {
        var response = exchangeRateRepository.findAll().stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());

        return response;
    }

    public ExchangeRateResponseDTO getByCodePair(String codePair) {
        var response = exchangeRateRepository.findByBaseCurrency_CodeAndTargetCurrency_Code(
                        CurrencyPairParser.getBaseCode(codePair),
                        CurrencyPairParser.getTargetCode(codePair)
                ).map(exchangeRateMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Exchange rate not found for code pair: " + codePair));

        return response;
    }

    public ExchangeRateResponseDTO update(String codePair, UpdateExchangeRateDTO request) {

        ExchangeRate exchangeRate = exchangeRateRepository.findByBaseCurrency_CodeAndTargetCurrency_Code(
                CurrencyPairParser.getBaseCode(codePair),
                CurrencyPairParser.getTargetCode(codePair)
        ).orElseThrow(() -> new NotFoundException("Exchange rate not found for code pair: " + codePair));

        exchangeRate.setRate(request.getRate());

        ExchangeRate saved = exchangeRateRepository.save(exchangeRate);
        var response = exchangeRateMapper.toDTO(saved);

        return response;
    }

    public void create(CreateExchangeRateDTO request) {

        String baseCode = request.getBaseCurrencyCode();
        String targetCode = request.getTargetCurrencyCode();

        if (exchangeRateRepository.existsByBaseCurrency_CodeAndTargetCurrency_Code(baseCode, targetCode)) {
            throw new AlreadyExistsException("Exchange rate already exists: " + baseCode + targetCode);
        }

        Currency baseCurrency = currencyRepository.findByCode(baseCode)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + baseCode));
        Currency targetCurrency = currencyRepository.findByCode(targetCode)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + targetCode));

        var result = new ExchangeRate();
        result.setBaseCurrency(baseCurrency);
        result.setTargetCurrency(targetCurrency);
        result.setRate(request.getRate());

        exchangeRateRepository.save(result);
    }

}
