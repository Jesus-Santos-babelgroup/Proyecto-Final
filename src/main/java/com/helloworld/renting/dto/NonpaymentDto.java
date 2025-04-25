package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class NonpaymentDto {
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long idClient;

    private String category;

    @NotNull
    @Positive
    private Integer startYear;

    @NotNull
    private BigDecimal paymentAmount;

    @NotNull
    private BigDecimal initialTotalImport;
}