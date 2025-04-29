package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class VehicleRequestedDto {
    @Positive
    private Long id;

    @NotBlank
    private RentingRequestDto rentingRequest;

    @NotBlank
    private VehicleDto vehicle;

    @NotBlank
    private String licensePlate;

    @NotNull
    private BigDecimal quotaFinalVehicle;
}
