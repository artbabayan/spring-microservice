package com.babayan.service.currency.feign.client.currency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Currency;
import java.util.List;

/**
 * @author by artbabayan
 */

@FeignClient(name = "CurrencyAPIClient", path = "/v1/currencies", url = "${microservices.currensy-service.host}")
public interface CurrencyAPIClient {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Currency>> getAll();

}
