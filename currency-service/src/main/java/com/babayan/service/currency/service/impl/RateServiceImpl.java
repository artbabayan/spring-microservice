package com.babayan.service.currency.service.impl;

import com.babayan.service.currency.common.exception.OperationFailedException;
import com.babayan.service.currency.common.exception.ValidationException;
import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.dto.Rate;
import com.babayan.service.currency.entity.RateEntity;
import com.babayan.service.currency.entity.VirtualCurrency;
import com.babayan.service.currency.repository.RateRepository;
import com.babayan.service.currency.service.CurrencyService;
import com.babayan.service.currency.service.RateService;
import com.babayan.service.currency.util.mappers.RateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.babayan.service.currency.util.DateHelper.convertFromDate;
import static com.babayan.service.currency.util.DateHelper.getTodayDate;
import static com.babayan.service.currency.util.FetchCurrency.fetchFromFixerIo;

/**
 * @author by artbabayan
 */
@Transactional(readOnly = true)
@Service
public class RateServiceImpl implements RateService {
    private static final Logger _logger = LoggerFactory.getLogger(RateServiceImpl.class);

    private RateRepository rateRepository;
    @Autowired public void setRateRepository(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    private CurrencyService currencyService;
    @Autowired public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private RateMapper mapper;
    @Autowired public void setMapper(RateMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Rate create(Long currencyId, Rate rate) {
        if (rate.getId() != null) {
            throw new ValidationException(String.format("%s not valid", rate.getId()));
        }
        if (rate.getCurrencyId() == null) {
            throw new ValidationException(String.format("%s not valid", rate));
        }

        Currency currency = currencyService.findById(currencyId);
        if (!currency.getId().equals(currencyId)) {
            throw new ValidationException("ID mismatch");
        }

        RateEntity rateEntity = mapper.toEntity(rate);
        rateEntity = rateRepository.save(rateEntity);

        return mapper.fromEntity(rateEntity);
    }

    @Transactional
    @Override
    public void addRates(Long currencyId, List<Rate> rates) {
        if (currencyId == null) {
            throw new OperationFailedException("Failed operation, null");
        }

        for (Rate newRate : rates) {
            if (newRate.getCurrencyId() == null || !newRate.getCurrencyId().equals(currencyId)) {
                throw new ValidationException("Currency not specified");
            }

            create(currencyId, newRate);
        }
    }

    @PostConstruct
    @Override
    public void generateCurrencyRates() {
        Date todayDate = getTodayDate();
        String convertFromDate = convertFromDate(todayDate);
        int count = currencyService.countByServiceDate(convertFromDate);

        if (count == 0) {
            VirtualCurrency virtual = fetchFromFixerIo();

            Currency currency = new Currency();
            currency.setBase(virtual.getBase());
            currency.setServiceDate(virtual.getDate());

            currency = currencyService.create(currency);
            Long currencyId = currency.getId();

            List<Rate> rates = new ArrayList<>();
            Map<String, String> map = virtual.getRates();
            map.forEach((name, value) -> {
                Rate rate = new Rate();
                rate.setName(name);
                rate.setRate(value);
                rate.setCurrencyId(currencyId);

                rates.add(rate);
            });
            addRates(currency.getId(), rates);

        } else if (count > 0) {
            _logger.info("Currency base is up to date");
        }
    }

}
