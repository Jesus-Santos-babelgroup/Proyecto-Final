package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class VehicleRequested {
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long requestId;

    @NotNull
    @Positive
    private Long vehicleId;

    @NotBlank
    private String licensePlate;

    @NotNull
    private BigDecimal quotaFinalVehicle;
}
