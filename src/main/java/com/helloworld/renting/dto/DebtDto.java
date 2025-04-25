package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class DebtDto {

    @Positive
    private Long id;

    @NotBlank
    private String nif;

    private String categoryCompany;

    @NotNull
    private BigDecimal amount;
}
