package com.babayan.service.currency.common.util.mappers;

import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.entity.CurrencyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by artbabayan
 */
@Component
public class CurrencyMapper {

    private ModelMapper modelMapper;
    @Autowired public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Currency fromEntity(CurrencyEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, Currency.class);
    }

    public CurrencyEntity toEntity(Currency currency) {
        if (currency == null) {
            return null;
        }
        return modelMapper.map(currency, CurrencyEntity.class);
    }

    public List<Currency> fromEntities(List<CurrencyEntity> entities) {
        List<Currency> dtoList = new ArrayList<>(entities.size());
        for (CurrencyEntity entity : entities) {
            dtoList.add(fromEntity(entity));
        }

        return dtoList;
    }

}
