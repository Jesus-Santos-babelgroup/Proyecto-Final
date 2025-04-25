package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.entities.Client;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvestmentUnderThreeGrossIncomeRule  extends ApprovedRule {
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        BigDecimal grossIncome = rulesContextDto.getGrossIncomeSelfEmployed();
        if(grossIncome == null)
            return true;

        BigDecimal investment = rulesContextDto.getTotalInvestment();

        return investment.compareTo(grossIncome.multiply(BigDecimal.valueOf(3))) <= 0;
    }

    @Override
    public String getName() {
        return "InvestmentUnderThreeGrossIncome";
    }
}
