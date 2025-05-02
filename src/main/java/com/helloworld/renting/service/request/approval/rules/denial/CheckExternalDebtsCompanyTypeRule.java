package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckExternalDebtsCompanyTypeRule implements DenialRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        List<DebtDto> debts = rulesContextDto.getDebts();
        if (debts == null) {
            return true;
        }
        return debts.stream()
                .map(DebtDto::getCategoryCompany)
                .noneMatch(category ->
                        "RENTING".equalsIgnoreCase(category) || "FINANCIERA".equalsIgnoreCase(category));
    }

    @Override
    public String getName() {
        return "Comprobar deudas externas que no sean tipo 'Renting' ni 'Financiera'";
    }
}
