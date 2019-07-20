package com.babayan.service.currency.service.impl;

import com.babayan.service.currency.common.exception.OperationFailedException;
import com.babayan.service.currency.common.exception.ValidationException;
import com.babayan.service.currency.common.util.mappers.RateMapper;
import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.dto.Rate;
import com.babayan.service.currency.entity.RateEntity;
import com.babayan.service.currency.repository.RateRepository;
import com.babayan.service.currency.service.CurrencyService;
import com.babayan.service.currency.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

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
    @Autowired @Qualifier("rateMapper") public void setMapper(RateMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        _logger.info("Rate service initialized");
    }

    /**
     * @see RateService#create(Long, Rate)
     */
    @Transactional
    @Override
    public Rate create(Long currencyId, @Valid Rate rate) {
        if (currencyId == null || rate.getCurrencyId() == null) {
            throw new ValidationException("Unable to process for null ID");
        }

        if (rate.getId() != null) {
            throw new ValidationException(String.format("Unable to verify if rate %s exists", rate.getId()));
        }

        Currency currency = currencyService.findById(currencyId);
        if (!currency.getId().equals(currencyId)) {
            throw new ValidationException("Rate's currency ID mismatch");
        }

        RateEntity rateEntity = mapper.toEntity(rate);
        try {
            rateEntity = rateRepository.save(rateEntity);
        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            throw new OperationFailedException(ex);
        }

        return mapper.fromEntity(rateEntity);
    }

    /**
     * @see RateService#addRates(Long, List)
     */
    @Transactional
    @Override
    public void addRates(Long currencyId, List<Rate> rates) {
        if (currencyId == null) {
            _logger.error("Unable to process for null ID");
            throw new OperationFailedException("Unable to process for null ID");
        }

        for (Rate rate : rates) {
            if (rate.getCurrencyId() == null) {
                _logger.error("Currency not specified");
                throw new ValidationException("Currency not specified");
            }

            if (!rate.getCurrencyId().equals(currencyId)) {
                _logger.error("Rate's currency ID mismatch");
                throw new ValidationException("Rate's currency ID mismatch");
            }

            create(currencyId, rate);
        }
    }

}
