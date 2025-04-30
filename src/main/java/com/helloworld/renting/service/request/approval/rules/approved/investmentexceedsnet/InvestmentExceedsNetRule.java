package com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidMappedDataException;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvestmentExceedsNetRule implements ApprovedRule {

    private final InvestmentExceedsNetMapper mapper;

    public InvestmentExceedsNetRule(InvestmentExceedsNetMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        BigDecimal netIncomeEmployed = mapper.getNetIncomeEmployed(rentingRequestDto.getId());
        BigDecimal netIncomeSelfEmployed = mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId());
        validateNetIncome(netIncomeEmployed, "Employed");
        validateNetIncome(netIncomeSelfEmployed, "Self-employed");
        BigDecimal netIncome = netIncomeEmployed.add(netIncomeSelfEmployed);
        BigDecimal investment = rentingRequestDto.getQuotaFinal();
        validateInvestment(investment);
        return investment.compareTo(netIncome) <= 0;
    }

    void validateInvestment(BigDecimal investment) {

        if (investment == null) {
            throw new InvalidRentingRequestDtoException("Investment cannot be null");
        }
        if (investment.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRentingRequestDtoException("Investment cannot be negative");
        }
    }

    void validateNetIncome(BigDecimal netIncome, String incomeType) {
        if (netIncome == null) {
            throw new InvalidMappedDataException(incomeType + " net income cannot be null");
        }
        if (netIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMappedDataException(incomeType + " net income cannot be negative");
        }
    }

    @Override
    public String getName() {
        return "InvestmentExceedsNetRule";
    }
}