package com.helloworld.renting.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RulesContextDto {
    private Long requestId;
    private BigDecimal totalInvestment;
    private Integer monthsHired;
    private LocalDate contractingDate;
    private LocalDate startDate;
    private String clientNif;
    private LocalDate clientBirthDate;
    private String clientNationality;
    private Integer clientScoring;
    private LocalDate employmentStartDate;
    private BigDecimal netIncomeEmployed;
    private BigDecimal netIncomeSelfEmployed;
    private BigDecimal grossIncomeSelfEmployed;
    private List<DebtDto> debts;
    private List<NonpaymentDto> nonpayments;
    private List<InformaDto> informaRecords;
    private Double Monthly_quota;
    private boolean previouslyRejected;
    private boolean previouslyApprovedWithGuarantee;
}
