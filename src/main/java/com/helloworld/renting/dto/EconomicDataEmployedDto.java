package com.helloworld.renting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helloworld.renting.exceptions.attributes.InvalidEmployedDataDtoException;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EconomicDataEmployedDto {

    @NotBlank
    private String cif;

    @NotNull
    @DecimalMin(value = "0.01", message = "El gross income debe ser mayor que 0")
    private BigDecimal grossIncome;

    @NotNull
    private BigDecimal netIncome;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    @Positive
    private Integer yearEntry;
}
