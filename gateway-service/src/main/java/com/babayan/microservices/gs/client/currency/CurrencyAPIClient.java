package com.babayan.microservices.gs.client.currency;

import com.babayan.microservices.gs.configuration.FeignConfiguration;
import com.babayan.service.currency.dto.Currency;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * @author by artbabayan
 */

@FeignClient(name = "Currency API client", path = "/v1/currencies", url = "${microservices.ta-accounts.host}", configuration = FeignConfiguration.class)
public interface CurrencyAPIClient {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Currency>> getAll();

}
