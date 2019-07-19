package com.babayan.service.currency.controller;

import com.babayan.service.currency.service.fixerio.FixerioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by artbabayan
 */
@RequestMapping(value = "/api/v1/fixerio")
@RestController
public class FixerioController {
    private static final Logger _logger = LoggerFactory.getLogger(FixerioController.class);


    private FixerioService fixerioService;
    @Autowired public void setFixerioService(FixerioService fixerioService) {
        this.fixerioService = fixerioService;
    }

    /**
     * Initializes a new instance of the class.
     */
    public FixerioController() {
        _logger.info("Fixerio API initialized");
    }

    /**
     *
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void rateManualUpdate() {
        fixerioService.generateData();
    }

}
