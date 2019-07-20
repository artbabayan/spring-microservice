package com.babayan.service.currency.service.fixerio;

import com.babayan.service.currency.common.exception.OperationFailedException;
import com.babayan.service.currency.configuration.FixerioConfig;
import com.babayan.service.currency.dto.Currency;
import com.babayan.service.currency.dto.FixerioCurrency;
import com.babayan.service.currency.dto.Rate;
import com.babayan.service.currency.service.CurrencyService;
import com.babayan.service.currency.service.RateService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.babayan.service.currency.common.util.helpers.DateHelper.convertFromDate;
import static com.babayan.service.currency.common.util.helpers.DateHelper.getTodayDate;
import static com.babayan.service.currency.common.util.helpers.JsonHelper.fromJson;
import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * @author by artbabayan
 */
@Transactional(readOnly = true)
@Service
public class FixerioServiceImpl implements FixerioService {
    private static final Logger _logger = LoggerFactory.getLogger(FixerioServiceImpl.class);

    private CurrencyService currencyService;
    @Autowired @Lazy public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private RateService rateService;
    @Autowired @Lazy public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }

    private FixerioConfig config;
    @Autowired public void setConfig(FixerioConfig config) {
        this.config = config;
    }

    @PostConstruct
    private void init() {
        _logger.info("FixerioService service initialized");
    }

    // region <SERVICE>

    /**
     * Method checks current courses in the database and
     * generate data and updates course rate from the
     * resource(@link https://fixer.io/), if they do not exist in db.
     *
     * @see FixerioService#generateData()
     */
    @Transactional
    @Override
    public List<Rate> generateData() {
        Date todayDate = getTodayDate();
        String convertFromDate = convertFromDate(todayDate);
        int count = currencyService.countByServiceDate(convertFromDate);
        List<Rate> rates = new ArrayList<>();

        if (count == 0) {
            FixerioCurrency fixerioCurrency = fetchFromFixerIo();

            Currency currency = new Currency();
            currency.setBase(fixerioCurrency.getBase());
            currency.setServiceDate(fixerioCurrency.getDate());

            currency = currencyService.create(currency);
            Long currencyId = currency.getId();

//            List<Rate> rates = new ArrayList<>();
            Map<String, String> map = fixerioCurrency.getRates();
            map.forEach((name, value) -> {
                Rate rate = new Rate();
                rate.setName(name);
                rate.setRate(value);
                rate.setCurrencyId(currencyId);

                rates.add(rate);
            });
            rateService.addRates(currency.getId(), rates);

        } else if (count > 0) {
            _logger.info("Currency base is up to date");
        }

        return rates;
    }

    // endregion

    // region <HELPER>

    private FixerioCurrency fetchFromFixerIo() {
        FixerioCurrency fixerioCurrency = new FixerioCurrency();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(config.getUrl().concat(config.apiKey));

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                fixerioCurrency = fromJson(line, FixerioCurrency.class);
                if (!fixerioCurrency.isSuccess()) {
                    throw new OperationFailedException(String.format("Operation can not continue, success=%s", fixerioCurrency.isSuccess()));
                }
            }
        } catch (IOException ex) {
            _logger.error("Response failed", ex.getMessage());
            ex.printStackTrace();
        }

        return fixerioCurrency;
    }

    // endregion

}
