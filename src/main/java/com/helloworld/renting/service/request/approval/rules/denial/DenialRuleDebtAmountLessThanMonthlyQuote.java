package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class DenialRuleDebtAmountLessThanMonthlyQuote extends DenialRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        validate(rulesContextDto);
        if (rulesContextDto.getDebts().isEmpty()) {
            return true;
        }

        Double monthlyQuota = rulesContextDto.getMonthly_quota();
        BigDecimal totalDebt = BigDecimal.ZERO;
        for (DebtDto debt : rulesContextDto.getDebts()) {
            totalDebt = totalDebt.add(debt.getAmount());
        }

        return monthlyQuota > totalDebt.doubleValue();
    }

    @Override
    public String getName() {
        return "Holder total debt is less than monthly quota";
    }

    private void validate(RulesContextDto rulesContextDto) {
        Objects.requireNonNull(rulesContextDto, "RulesContextDto no puede ser null");

        if (rulesContextDto.getMonthly_quota() == null) {
            throw new InvalidRulesContextDtoException("Cuota mensual no puede ser null.");
        } else if (rulesContextDto.getDebts() == null) {
            throw new InvalidRulesContextDtoException("Lista de deudas no puede ser null.");
        } else if (!validDebts(rulesContextDto.getDebts())) {
            throw new InvalidRulesContextDtoException("Algún valor de deuda es negativo.");
        }
    }

    private boolean validDebts(List<DebtDto> debts) {
        for (DebtDto debt : debts) {
            if (debt.getAmount() == null) {
                return false;
            }
            if (debt.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
                return false;
            }
        }
        return true;
    }
}
