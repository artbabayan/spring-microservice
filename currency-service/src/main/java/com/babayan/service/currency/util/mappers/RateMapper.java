package com.babayan.service.currency.util.mappers;

import com.babayan.service.currency.dto.Rate;
import com.babayan.service.currency.entity.RateEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by artbabayan
 */
public class RateMapper {

    private ModelMapper modelMapper;
    @Autowired public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Rate fromEntity(RateEntity rateEntity) {
        if (rateEntity == null) {
            return null;
        }

        return modelMapper.map(rateEntity, Rate.class);
    }

    public RateEntity toEntity(Rate rate) {
        if (rate == null) {
            return null;
        }
        return modelMapper.map(rate, RateEntity.class);
    }

    public List<Rate> fromEntities(List<RateEntity> entities) {
        List<Rate> dtoList = new ArrayList<>(entities.size());
        for (RateEntity entity : entities) {
            dtoList.add(fromEntity(entity));
        }
        return dtoList;
    }

}
