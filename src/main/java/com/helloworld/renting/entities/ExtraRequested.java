package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ExtraRequested {
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long vehicleRequestedId;

    @NotNull
    @Positive
    private Long extraVehicleId;
}
