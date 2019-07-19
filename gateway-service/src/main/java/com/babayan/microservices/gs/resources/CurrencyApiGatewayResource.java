package com.babayan.microservices.gs.resources;

import com.babayan.microservices.gs.client.currency.CurrencyAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by artbabayan
 */
@RestController
@RequestMapping(value = "/api/v1/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CurrencyApiGatewayResource {

    private CurrencyAPIClient currencyAPIClient;
    @Autowired public void setApiClient(CurrencyAPIClient currencyAPIClient) {
        this.currencyAPIClient = currencyAPIClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll() {
        return currencyAPIClient.getAll();
    }

}
