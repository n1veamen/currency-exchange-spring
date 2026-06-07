package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.currencyDTO.CreateCurrencyDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.exception.AlreadyExistsException;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.mapper.CurrencyMapper;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    public Currency createCurrency(CreateCurrencyDTO currency) {
        if ( currencyRepository.findByCode(currency.getCode( )).isPresent( ) ) {
            throw new AlreadyExistsException("Currency with this code already exists: " + currency.getCode( ));
        }

        return currencyRepository.save(currencyMapper.toEntity(currency));

    }

    public List<Currency> getAll( ) {
        return currencyRepository.findAll( );
    }

    public Currency getByCode(String code) {
        return currencyRepository.findByCode(code).orElseThrow(
                ( ) -> new NotFoundException("Currency not found: " + code)
        );
    }
}
