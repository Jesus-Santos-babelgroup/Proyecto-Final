package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class Informa {
    @Positive
    private Long id;

    @NotBlank
    private String cif;

    @NotBlank
    private String companyName;

    @NotBlank
    private String municipality;

    @NotBlank
    private String zipCode;

    @NotNull
    private BigDecimal amountSales;

    @NotNull
    private BigDecimal profitBeforeTax;

    @NotNull
    @Positive
    private Integer fiscalYear;
}
