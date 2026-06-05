package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.currencyDTO.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.currencyDTO.CurrencyResponceDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.mapper.CurrencyMapper;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    public CurrencyResponceDTO createCurrency(CreateCurrencyDTO currency) {
        Currency saved = currencyRepository.save(currencyMapper.toEntity(currency));
        return currencyMapper.toDto(saved);
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getByCode(String code) {
        return currencyRepository.findByCode(code);
    }

}
