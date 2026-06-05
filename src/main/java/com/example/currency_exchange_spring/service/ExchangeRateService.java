package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
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

    public List<ExchangeRateResponseDTO> getAll() {

        return rateRepository.findAll().stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());

    }

    public Optional<ExchangeRateResponseDTO> getByCurrencyPair(CurrencyPair currencyPair) {

        return rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                        currencyPair.getBaseCurrencyCode( ),
                        currencyPair.getTargetCurrencyCode( )
                ).map(exchangeRateMapper::toDTO);

    }

    public ExchangeRateResponseDTO update(CurrencyPair currencyPair, UpdateExchangeRateDTO updateExchangeRateDTO) {

        ExchangeRate exchangeRate = rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                currencyPair.getBaseCurrencyCode(),
                currencyPair.getTargetCurrencyCode()
        ).orElseThrow( () -> new RuntimeException("Not found") );

        ExchangeRate saved = rateRepository.save(exchangeRate);

        return exchangeRateMapper.toDTO(saved);

    }

    public ExchangeRateResponseDTO create(CreateExchangeRateDTO createExchangeRateDTO) {

        ExchangeRate created = rateRepository.save( exchangeRateMapper.toEntity( createExchangeRateDTO ) );
        return exchangeRateMapper.toDTO(created);

    }

}
