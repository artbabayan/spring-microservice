package com.babayan.service.currency;

import com.babayan.service.currency.dto.Rate;
import com.babayan.service.currency.service.fixerio.FixerioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

/**
 * @author by artbabayan
 */
@EnableJpaRepositories(basePackages = "com.babayan.service.currency.repository")
@SpringBootApplication
public class CurrencyApplication {

    private FixerioService fixerioService;
    @Autowired public void setFixerioService(FixerioService fixerioService) {
        this.fixerioService = fixerioService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public List<Rate> qaq() {
        return fixerioService.generateData();
    }

}
