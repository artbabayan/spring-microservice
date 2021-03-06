package com.babayan.service.currency.service.impl;

import com.babayan.service.currency.common.exception.OperationFailedException;
import com.babayan.service.currency.common.exception.ValidationException;
import com.babayan.service.currency.common.util.mappers.CurrencyMapper;
import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.entity.CurrencyEntity;
import com.babayan.service.currency.repository.CurrencyRepository;
import com.babayan.service.currency.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author by artbabayan
 */
@Transactional(readOnly = true)
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger _logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private CurrencyRepository repository;
    @Autowired public void setRepository(CurrencyRepository repository) {
        this.repository = repository;
    }

    private CurrencyMapper mapper;
    @Autowired public void setMapper(CurrencyMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        _logger.info("Currency service initialized");
    }

    // region <SERVICE>

    /**
     * @see CurrencyService#create(Currency)
     */
    @Transactional
    @Override
    public Currency create(@Valid @NotNull Currency currency) {
        if (currency.getId() != null) {
            throw new ValidationException(String.format("%s not valid", currency.getId()));
        }

        CurrencyEntity entity = mapper.toEntity(currency);
        try {
            entity = repository.save(entity);
            _logger.info("Currency was successfully created");
        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            throw new OperationFailedException(ex);
        }

        return mapper.fromEntity(entity);
    }

    /**
     * @see CurrencyService#findById(Long)
     */
    @Override
    public Currency findById(Long id) {
        Optional<CurrencyEntity> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException(String.format("Currency with id %s not found", id));

        }

        return mapper.fromEntity(optional.get());
    }

    /**
     * @see CurrencyService#findAll()
     */
    @Override
    public List<Currency> findAll() {
        return mapper.fromEntities(repository.findAll());
    }

    /**
     * @see CurrencyService#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException ex) {
            _logger.error(ex.getMessage(), ex);
            throw new OperationFailedException(ex);
        }
    }

    /**
     * @see CurrencyService#countByServiceDate(String)
     */
    @Override
    public int countByServiceDate(String serviceDate) {
        return repository.countByServiceDate(serviceDate);
    }

    // endregion

}
