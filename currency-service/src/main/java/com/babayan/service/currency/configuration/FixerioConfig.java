package com.babayan.service.currency.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author by artbabayan
 */
@Getter
@Setter
@Configuration
public class FixerioConfig {

    @Value("${fixerio.service.url}")
    public String url;

    @Value("${fixerio.service.apikey}")
    public String apiKey;

}
