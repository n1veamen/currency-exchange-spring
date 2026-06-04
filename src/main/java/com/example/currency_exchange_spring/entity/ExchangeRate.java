package com.example.currency_exchange_spring.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "base_currency_id", nullable = false)
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id", nullable = false)
    private Currency targetCurrency;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal rate;

}
