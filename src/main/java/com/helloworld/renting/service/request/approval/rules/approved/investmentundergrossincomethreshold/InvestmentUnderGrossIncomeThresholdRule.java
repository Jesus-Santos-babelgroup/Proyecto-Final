package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
public class InvestmentUnderGrossIncomeThresholdRule implements ApprovedRule {

    private final InvestmentUnderGrossIncomeThresholdMapper mapper;

    private final InvestmentUnderGrossIncomeThresholdProperties ruleProperties;

    public InvestmentUnderGrossIncomeThresholdRule(InvestmentUnderGrossIncomeThresholdMapper mapper, InvestmentUnderGrossIncomeThresholdProperties ruleProperties) {
        this.mapper = mapper;
        this.ruleProperties = ruleProperties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {

        Long clientId = rentingRequestDto.getClient().getId();
        Long requestId = rentingRequestDto.getId();

        List<EconomicDataSelfEmployedDto> data = mapper.findByClientId(clientId);
        BigDecimal investment = mapper.findTotalInvestment(requestId);

        if(data.isEmpty()) return true;

        EconomicDataSelfEmployedDto dto = data.stream().max(Comparator.comparing(EconomicDataSelfEmployedDto::getYearEntry)).orElseThrow(() -> new InvalidRentingRequestDtoException("No valid economic data found"));
        BigDecimal grossIncome = dto.getGrossIncome();

        validateTotalInvestment(investment);
        validateGrossIncome(grossIncome);

        int multiplier = ruleProperties.getMultiplier();

        return investment.compareTo(grossIncome.multiply(BigDecimal.valueOf(multiplier))) <= 0;
    }

    void validateTotalInvestment(BigDecimal totalInvestment) {

        if (totalInvestment == null) {
            throw new InvalidRentingRequestDtoException("Total Investment cannot be null");
        }
        if (totalInvestment.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRentingRequestDtoException("Total Investment cannot be negative");
        }
    }

    void validateGrossIncome(BigDecimal grossIncome) {

        if (grossIncome == null) {
            throw new InvalidRentingRequestDtoException("Gross income cannot be null");
        }
        if (grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRentingRequestDtoException("Gross income cannot be negative");
        }
    }

    @Override
    public String getName() {
        return "InvestmentUnderGrossIncomeThreshold";
    }
}
