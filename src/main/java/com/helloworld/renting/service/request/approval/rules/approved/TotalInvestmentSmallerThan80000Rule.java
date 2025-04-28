package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TotalInvestmentSmallerThan80000Rule extends ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {

        BigDecimal totalInvestment = rulesContextDto.getTotalInvestment();
        BigDecimal limit = BigDecimal.valueOf(80000);

        if (totalInvestment == null) {
            throw new InvalidRulesContextDtoException("Total Investment cannot be null");
        }
        if (totalInvestment.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRulesContextDtoException("Total Investment cannot be negative");
        }
        return (totalInvestment.compareTo(limit) < 0);
    }

    @Override
    public String getName() {
        return "TotalInvestmentSmallerThan80k";
    }

}
