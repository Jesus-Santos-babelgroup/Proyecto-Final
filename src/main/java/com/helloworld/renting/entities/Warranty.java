package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class Warranty {
    @Positive
    private Long id;

    @NotNull
    private WarrantyType warrantyType;

    @NotNull
    private BigDecimal warrantyImport;

    @NotBlank
    private String nif;

    private String description;
}
