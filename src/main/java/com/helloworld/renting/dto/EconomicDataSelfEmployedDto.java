package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class EconomicDataSelfEmployedDto {

    @Positive
    private Long id;

    @NotBlank
    private ClientDto client;

    @NotNull
    private BigDecimal grossIncome;

    @NotNull
    private BigDecimal netIncome;

    @NotNull
    @Positive
    private Integer yearEntry;
}
