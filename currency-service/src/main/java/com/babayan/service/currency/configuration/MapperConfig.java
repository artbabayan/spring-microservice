package com.babayan.service.currency.configuration;

import com.babayan.service.currency.util.mappers.CurrencyMapper;
import com.babayan.service.currency.util.mappers.RateMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by artbabayan
 */
@Configuration
public class MapperConfig {

    @Bean(name = "currencyMapper")
    public CurrencyMapper currencyMapper() {
        return new CurrencyMapper();
    }

    @Bean(name = "rateMapper")
    public RateMapper rateMapper() {
        return new RateMapper();
    }

}
