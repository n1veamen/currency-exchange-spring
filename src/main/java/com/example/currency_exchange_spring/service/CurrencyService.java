package com.example.currency_exchange_spring.service;

import com.example.currency_exchange_spring.dto.CreateCurrencyDTO;
import com.example.currency_exchange_spring.dto.CurrencyResponseDTO;
import com.example.currency_exchange_spring.exception.AlreadyExistsException;
import com.example.currency_exchange_spring.exception.NotFoundException;
import com.example.currency_exchange_spring.mapper.CurrencyMapper;
import com.example.currency_exchange_spring.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    public void create(CreateCurrencyDTO request) {
        if (currencyRepository.existsByCode(request.getCode())) {
            throw new AlreadyExistsException("Currency with this code already exists: " + request.getCode());
        }

        currencyRepository.save(
                currencyMapper.toEntity(request)
        );
    }

    public List<CurrencyResponseDTO> getAll() {
        var response = currencyRepository.findAll().stream()
                .map(currencyMapper::toDTO)
                .collect(Collectors.toList());

        return response;
    }

    public CurrencyResponseDTO getByCode(String code) {
        var response = currencyRepository.findByCode(code)
                .map(currencyMapper::toDTO)
                .orElseThrow(() -> {
                    return new NotFoundException("Currency not found: " + code);
                });

        return response;
    }
}
