package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.mapper.DebtMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebtLessThanMonthlyQuotaRule implements ApprovedRule {

    private final DebtMapper debtMapper;

    public DebtLessThanMonthlyQuotaRule(DebtMapper debtMapper) {
        this.debtMapper = debtMapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        List<Debt> debts = debtMapper.findDebtsByNif(rentingRequestDto.getClient().getNif());
        Double debt = (debts != null ? debts.stream()
                .map(d -> d.getAmount().doubleValue())
                .reduce(0.0, Double::sum) : 0.0);
        Double monthlyQuota = rentingRequestDto.getQuotaBaseMonthlyFee().doubleValue();
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

}