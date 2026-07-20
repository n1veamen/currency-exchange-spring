package com.example.currency_exchange_spring.mapper;

import com.example.currency_exchange_spring.dto.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    ExchangeRateResponseDTO toDTO(ExchangeRate exchangeRate);

}
