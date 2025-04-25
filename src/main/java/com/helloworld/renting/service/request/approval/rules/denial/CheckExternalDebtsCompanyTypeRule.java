package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RulesContextDto;

import java.util.List;

public class CheckExternalDebtsCompanyTypeRule extends DenialRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        List<DebtDto> debts = rulesContextDto.getDebts();
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
