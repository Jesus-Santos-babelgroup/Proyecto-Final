package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EconomicDataEmployedDto {

    @Positive
    private Long id;

    @NotBlank
    private ClientDto client;

    @NotBlank
    private String cif;

    @NotNull
    private BigDecimal grossIncome;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Positive
    private Integer yearEntry;
}
