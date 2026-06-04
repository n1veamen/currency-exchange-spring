package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.ExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import com.example.currency_exchange_spring.mapper.ExchangeRateMapper;
import com.example.currency_exchange_spring.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository rateRepository;

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    public List<ExchangeRateDTO> getAll() {
        return rateRepository.findAll().stream()
                .map(exchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ExchangeRateDTO> getByCurrencyPair(String baseCurrencyCode, String target) {
        return rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(baseCurrencyCode, target)
                .map(exchangeRateMapper::toDTO);
    }

    public ExchangeRate update(CreateExchangeRateDTO createExchangeRateDTO) {
        return rateRepository.save(exchangeRateMapper.toEntity(createExchangeRateDTO));
    }

}
