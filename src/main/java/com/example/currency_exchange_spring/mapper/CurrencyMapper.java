package com.example.currency_exchange_spring.mapper;

import com.example.currency_exchange_spring.dto.currencyDTO.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.currencyDTO.CurrencyResponceDTO;
import com.example.currency_exchange_spring.entity.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toEntity(CreateCurrencyDTO dto);

    Currency toEntity(CurrencyResponceDTO dto);

    CurrencyResponceDTO toDto(Currency currency);

}