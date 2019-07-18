package com.babayan.service.currency.util;

import com.babayan.service.currency.common.exception.OperationFailedException;
import com.babayan.service.currency.entity.VirtualCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.babayan.service.currency.util.JsonHelper.fromJson;
import static com.google.common.net.HttpHeaders.USER_AGENT;

/**
 * @author by artbabayan
 */
@Configuration
public class FetchCurrency {
    private static final Logger _logger = LoggerFactory.getLogger(FetchCurrency.class);

//    @Value("${service.url}")
//    private static String url;
//
//    @Value("${service.key}")
//    private static String apiKey;

    private static String fullUrl = "http://data.fixer.io/api/latest?access_key=b11a888bc2599f57e7a37baf1f4fc410";
//    private static String fullUrl = "";

    public static VirtualCurrency fetchFromFixerIo() {
        VirtualCurrency virtual = new VirtualCurrency();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(fullUrl);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                virtual = fromJson(line, VirtualCurrency.class);
                if (!virtual.isSuccess()) {
                    throw new OperationFailedException(String.format("Operation can not continue, success=%s", virtual.isSuccess()));
                }
            }
        } catch (IOException ex) {
            _logger.error("Response failed", ex.getMessage());
            ex.printStackTrace();
        }

        return virtual;
    }

}
