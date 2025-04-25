package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class EconomicDataSelfEmployed {

    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long clientId;

    @NotNull
    private BigDecimal grossIncome;

    @NotNull
    private BigDecimal netIncome;

    @NotNull
    @Positive
    private Integer yearEntry;
}
