package com.babayan.service.currency.controller;

import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author by artbabayan
 */
@RequestMapping(value = "/api/v10/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CurrencyController {
    private static final Logger _logger = LoggerFactory.getLogger(CurrencyController.class);

    private CurrencyService service;
    @Autowired public void setService(CurrencyService service) {
        this.service = service;
    }

    /**
     * Initializes a new instance of the class.
     */
    public CurrencyController() {
        _logger.info("Currency API initialized");
    }

    /**
     *
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Resource<Currency> findOne(@PathVariable("id") long id) {
        Currency currency = service.findById(id);
        Link selfLink = linkTo(methodOn(CurrencyController.class))
                .withSelfRel();

        return new Resource<>(currency, selfLink);
    }

    /**
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<Currency>> finadAll() {
        List<Resource<Currency>> resources = new ArrayList<>();

        List<Currency> currencies = service.findAll();
        for (Currency currency : currencies) {
            Link selfLink = linkTo(methodOn(CurrencyController.class)
                    .findOne(currency.getId()))
                    .withSelfRel()
                    .expand();
            resources.add(new Resource<>(currency, selfLink));
        }

        return resources;
    }

}
