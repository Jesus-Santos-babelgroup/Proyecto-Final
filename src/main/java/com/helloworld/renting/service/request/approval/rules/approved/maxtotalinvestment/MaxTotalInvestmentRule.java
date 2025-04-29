package com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment;

import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MaxTotalInvestmentRule implements ApprovedRule {

    private final MaxTotalInvestmentProperties rulesProperties;

    public MaxTotalInvestmentRule(MaxTotalInvestmentProperties rulesProperties) {
        this.rulesProperties = rulesProperties;
    }

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {

        BigDecimal totalInvestment = rulesContextDto.getTotalInvestment();
        BigDecimal limit = rulesProperties.getLimit();

        validateTotalInvestment(totalInvestment);
        validateMaxTotalInvestment(limit);

        return (totalInvestment.compareTo(limit) < 0);
    }

    void validateTotalInvestment(BigDecimal totalInvestment) {

        if (totalInvestment == null) {
            throw new InvalidRentingRequestDtoException("Total Investment cannot be null");
        }
        if (totalInvestment.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRentingRequestDtoException("Total Investment cannot be negative");
        }
    }

    void validateMaxTotalInvestment(BigDecimal limit) {

        if (limit == null) {
            throw new InvalidRentingRequestDtoException("Max Total Investment cannot be null");
        }
        if (limit.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRentingRequestDtoException("Max Total Investment cannot be negative");
        }
    }

    @Override
    public String getName() {
        return "TotalInvestmentSmallerThanMaxTotalInvestment";
    }

}
