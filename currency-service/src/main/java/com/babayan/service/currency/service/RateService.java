package com.babayan.service.currency.service;

import com.babayan.service.currency.dto.Rate;

import java.util.List;

/**
 * @author by artbabayan
 */
public interface RateService {

    Rate create(Long currencyId, Rate rate);

    void addRates(Long currencyId, List<Rate> rates);

    void generateCurrencyRates();
}
