package com.babayan.service.currency.service.fixerio;

import com.babayan.service.currency.dto.Rate;

import java.util.List;

/**
 * @author by artbabayan
 */
public interface FixerioService {

    List<Rate> generateData();

}
