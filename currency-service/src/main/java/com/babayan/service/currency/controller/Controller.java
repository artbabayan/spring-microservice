package com.babayan.service.currency.controller;

import com.babayan.service.currency.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by artbabayan
 */
@RequestMapping(value = "/api/v1/currencies")
@RestController
public class Controller {

    private RateService rateService;
    @Autowired public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void generateCourse() {
        rateService.generateCurrencyRates();
    }

}
