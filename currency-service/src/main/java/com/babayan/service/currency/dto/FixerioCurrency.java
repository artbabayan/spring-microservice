package com.babayan.service.currency.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author by artbabayan
 */
@Getter
@Setter
public class FixerioCurrency implements Serializable {

    private String base;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String date;

    private boolean isSuccess;

    Map<String, String> rates;

}
