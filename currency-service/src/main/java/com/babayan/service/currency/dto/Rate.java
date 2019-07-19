package com.babayan.service.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author by artbabayan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String rate;

    @Min(1)
    @NotNull
    private Long currencyId;

}
