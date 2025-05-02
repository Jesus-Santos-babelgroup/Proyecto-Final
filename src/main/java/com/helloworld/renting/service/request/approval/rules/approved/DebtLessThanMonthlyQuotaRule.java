/*
package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.DebtDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebtLessThanMonthlyQuotaRule implements ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        List<DebtDto> debts = rulesContextDto.getDebts();
        Double debt = (debts != null ? debts.stream()
                .map(d -> d.getAmount().doubleValue())
                .reduce(0.0, Double::sum) : 0.0);
        Double monthlyQuota = rulesContextDto.getMonthly_quota();
        if (monthlyQuota == null) {
            throw new IllegalArgumentException("Monthly quota cannot be null");
        }
        if (monthlyQuota < 0) {
            throw new IllegalArgumentException("Monthly quota cannot be negative");
        }
        return debt < monthlyQuota;
    }

    @Override
    public String getName() {
        return "DebtLessThanMonthlyQuota";
    }

}*/
