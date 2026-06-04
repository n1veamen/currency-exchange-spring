package com.example.currency_exchange_spring.mapper;

import com.example.currency_exchange_spring.dto.currencyDTO.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.ExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.entity.Currency;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    ExchangeRate toEntity(ExchangeRateDTO exchangeRateDTO);

    ExchangeRate toEntity(CreateExchangeRateDTO createExchangeRateDTO);

    ExchangeRateDTO toDTO(ExchangeRate exchangeRate);

}
