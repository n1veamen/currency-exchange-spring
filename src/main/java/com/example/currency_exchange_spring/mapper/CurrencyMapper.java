package com.example.currency_exchange_spring.mapper;

import com.example.currency_exchange_spring.dto.request.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.response.CurrencyResponseDTO;
import com.example.currency_exchange_spring.entity.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toEntity(CreateCurrencyDTO dto);

    Currency toEntity(CurrencyResponseDTO dto);

    CurrencyResponseDTO toDTO(Currency currency);

}