package com.babayan.service.currency.service;

import com.babayan.service.currency.dto.Currency;

/**
 * @author by artbabayan
 */
public interface CurrencyService {

    Currency create(Currency currency);

    Currency findById(Long id);

    void deleteById(Long id);

    int countByServiceDate(String serviceDate);

}
