package com.babayan.microservices.gs.configuration;

import feign.Contract;
import feign.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

    @Bean
    Logger.Level feignLoggerLevel(@Value("${feign.client.config.default.loggerLevel: FULL}") String debugLevel) {
        return Logger.Level.valueOf(debugLevel);
    }

}
