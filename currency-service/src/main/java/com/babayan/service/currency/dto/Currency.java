package com.babayan.service.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author by artbabayan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {

    private Long id;

    @NotEmpty
    private String base;

    @NotEmpty
    private String serviceDate;

    private List<Rate> rates;

}
