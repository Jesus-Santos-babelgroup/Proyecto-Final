package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class ExtraRequestedDto {
    @Positive
    private Long id;

    @NotBlank
    private VehicleRequestedDto vehicle;

    @NotBlank
    private ExtraVehicleDto extraVehicle;
}
