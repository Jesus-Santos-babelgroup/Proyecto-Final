package com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet;


import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
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
        BigDecimal netIncome = netIncomeEmployed.add(netIncomeSelfEmployed);
        BigDecimal investment = rentingRequestDto.getQuotaFinal();
        return investment.compareTo(netIncome) <= 0;
    }

    @Override
    public String getName() {
        return "InvestmentExceedsNetRule";
    }
}