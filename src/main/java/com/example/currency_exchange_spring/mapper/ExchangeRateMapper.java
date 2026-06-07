package com.example.currency_exchange_spring.mapper;

import com.example.currency_exchange_spring.dto.exchangeRateDTO.CreateExchangeRateDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.ExchangeRateResponseDTO;
import com.example.currency_exchange_spring.dto.exchangeRateDTO.UpdateExchangeRateDTO;
import com.example.currency_exchange_spring.entity.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    ExchangeRate toEntity(ExchangeRateResponseDTO exchangeRateDTO);

    ExchangeRateResponseDTO toDTO(ExchangeRate exchangeRate);

    ExchangeRate toEntity(UpdateExchangeRateDTO updateExchangeRateDTO);

}
