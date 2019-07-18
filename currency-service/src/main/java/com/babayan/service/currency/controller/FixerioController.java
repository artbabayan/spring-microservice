package com.babayan.service.currency.controller;

import com.babayan.service.currency.service.fixerio.FixerioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by artbabayan
 */
@RequestMapping(value = "/api/v1/fixerios")
@RestController
public class FixerioController {

    private FixerioService fixerioService;
    @Autowired public void setFixerioService(FixerioService fixerioService) {
        this.fixerioService = fixerioService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void rateManualUpdate() {
        fixerioService.generateData();
    }

}
