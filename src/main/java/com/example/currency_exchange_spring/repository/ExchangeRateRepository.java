package com.example.currency_exchange_spring.repository;

import com.example.currency_exchange_spring.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    public Optional<ExchangeRate> findByBaseCurrencyCodeAndTargetCurrencyCode(
            String baseCurrencyCode,
            String targetCurrencyCode
    );

    public List<ExchangeRate> findByBaseCurrencyCode(String code);
}
