package com.helloworld.renting.dto;

import com.helloworld.renting.entities.FinalResultType;
import com.helloworld.renting.entities.PreResultType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentingRequestDto {

    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long clientId;

    @NotNull
    @Positive
    private Long warrantyId;

    @NotNull
    private BigDecimal quotaFinal;

    @NotNull
    private BigDecimal quotaBaseMonthlyFee;

    @NotNull
    private LocalDate contractingDate;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private PreResultType preResultType;

    private FinalResultType finalResultType;

    @NotNull
    @Positive
    private Integer monthsHired;
}
