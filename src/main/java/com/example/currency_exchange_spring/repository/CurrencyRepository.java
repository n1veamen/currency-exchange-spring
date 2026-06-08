package com.example.currency_exchange_spring.repository;

import com.example.currency_exchange_spring.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCode(String code);

    boolean existsByCode(String code);
}
