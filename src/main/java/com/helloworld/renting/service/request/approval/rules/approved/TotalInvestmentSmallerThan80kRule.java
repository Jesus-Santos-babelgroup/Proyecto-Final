package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RulesContextDto;

import java.math.BigDecimal;
import java.util.List;

public class TotalInvestmentSmallerThan80kRule extends ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {

        BigDecimal totalInvestment = rulesContextDto.getTotalInvestment();
        BigDecimal limit = BigDecimal.valueOf(80000);

        if (totalInvestment == null) {
            throw new IllegalArgumentException("Total Investment cannot be null");
        }
        if (totalInvestment.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total Investment cannot be negative");
        }
        return (totalInvestment.compareTo(limit) < 0);
    }

    @Override
    public String getName() {
        return "TotalInvestmentSmallerThan80k";
    }

}
