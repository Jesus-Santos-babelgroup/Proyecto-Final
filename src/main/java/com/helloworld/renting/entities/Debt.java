package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class Debt {

    @Positive
    private Long id;

    @NotBlank
    private String nif;

    private String categoryCompany;

    @NotNull
    private BigDecimal amount;
}
