package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EconomicDataEmployed {

    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long clientId;

    @NotBlank
    private String cif;

    @NotNull
    private BigDecimal grossIncome;

    @NotNull
    private BigDecimal netIncome;


    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Positive
    private Integer yearEntry;

    @NotNull
    private BigDecimal netIncome;

}
