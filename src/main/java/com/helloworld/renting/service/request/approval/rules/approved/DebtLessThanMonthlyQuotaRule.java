package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;

public class DebtLessThanMonthlyQuotaRule extends ApprovedRule{

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        Double debt = rulesContextDto.getDebts()
                .stream()
                .map(debts -> debts.getAmount().doubleValue())
                .reduce(0.0, Double::sum);
        Double monthly_quota = rulesContextDto.getMonthly_quota();
        return debt < monthly_quota;
    }

    @Override
    public String getName() {
        return "DebtLessThanMonthlyQuota";
    }

}