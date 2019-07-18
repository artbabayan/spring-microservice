package com.babayan.service.currency.service;

import com.babayan.service.currency.dto.Currency;

import java.util.List;

/**
 * @author by artbabayan
 */
public interface CurrencyService {

    Currency create(Currency currency);

    Currency findById(Long id);

    List<Currency> findAll();

    void deleteById(Long id);

    int countByServiceDate(String serviceDate);

}
