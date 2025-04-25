package com.helloworld.renting.service.request.approval.rules.approved;


import com.helloworld.renting.dto.RulesContextDto;

import java.math.BigDecimal;

public class InvestmentExceedsNetRule extends ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        BigDecimal netIncome = rulesContextDto.getNetIncomeEmployed().add(rulesContextDto.getNetIncomeSelfEmployed());
        BigDecimal investment = rulesContextDto.getTotalInvestment();
        return investment.compareTo(netIncome) <= 0;
    }

    @Override
    public String getName() {
        return "InvestmentExceedsNetRule";
    }
}