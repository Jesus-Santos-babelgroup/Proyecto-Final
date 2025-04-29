package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvestmentUnderGrossIncomeThresholdRule implements ApprovedRule {

    private final InvestmentUnderGrossIncomeThresholdProperties ruleProperties;

    public InvestmentUnderGrossIncomeThresholdRule(InvestmentUnderGrossIncomeThresholdProperties ruleProperties) {
        this.ruleProperties = ruleProperties;
    }

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        BigDecimal grossIncome = rulesContextDto.getGrossIncomeSelfEmployed();
        if (grossIncome == null)
            return true;

        BigDecimal investment = rulesContextDto.getTotalInvestment();

        validateTotalInvestment(investment);
        validateGrossIncome(grossIncome);

        int multiplier = ruleProperties.getMultiplier();

        return investment.compareTo(grossIncome.multiply(BigDecimal.valueOf(multiplier))) <= 0;
    }

    void validateTotalInvestment(BigDecimal totalInvestment) {

        if (totalInvestment == null) {
            throw new InvalidRulesContextDtoException("Total Investment cannot be null");
        }
        if (totalInvestment.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRulesContextDtoException("Total Investment cannot be negative");
        }
    }

    void validateGrossIncome(BigDecimal grossIncome) {
        if (grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRulesContextDtoException("Total Investment cannot be negative");
        }
    }

    @Override
    public String getName() {
        return "InvestmentUnderGrossIncomeThreshold";
    }
}
