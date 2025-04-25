package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class VehicleDto {
    @Positive
    private Long id;

    @NotBlank
    private String model;

    @NotBlank
    private String brand;

    @NotNull
    private BigDecimal investment;

    @NotNull
    private BigDecimal quotaBase;

    @NotNull
    @Positive
    private Integer cubicCapacity;

    @NotNull
    @Positive
    private Integer power;

    @NotBlank
    private String color;

    @NotNull
    @Positive
    private Integer numberSeats;
}