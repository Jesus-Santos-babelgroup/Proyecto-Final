package com.helloworld.renting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helloworld.renting.exceptions.attributes.InvalidEmployedDataDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidSelfEmployedDataDtoException;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class EconomicDataSelfEmployedDto {

    @NotNull
    @DecimalMin(value = "0.01", message = "El gross income debe ser mayor que 0")
    private BigDecimal grossIncome;

    @NotNull
    private BigDecimal netIncome;

    @NotNull
    @Positive
    private Integer yearEntry;

}
