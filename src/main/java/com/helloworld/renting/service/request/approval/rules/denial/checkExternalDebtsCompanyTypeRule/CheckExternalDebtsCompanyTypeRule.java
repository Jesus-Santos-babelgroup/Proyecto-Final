package com.helloworld.renting.service.request.approval.rules.denial.checkExternalDebtsCompanyTypeRule;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.mapper.DebtMapper;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckExternalDebtsCompanyTypeRule implements DenialRule {

    private final DebtMapper debtMapper;
    private final CheckExternalDebtsCompanyTypeProperties debtProperties;


    public CheckExternalDebtsCompanyTypeRule(DebtMapper debtMapper, CheckExternalDebtsCompanyTypeProperties debtProperties) {
        this.debtMapper = debtMapper;
        this.debtProperties = debtProperties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        ClientDto client  = rentingRequestDto.getClient();
        List<Debt> debts = debtMapper.findDebtsByNif(client.getNif());
        if (debts == null || debts.isEmpty()) {
            return true;
        }
        return debts.stream()
                .map(Debt::getCategoryCompany)
                .noneMatch(category -> debtProperties.getInvalidCategories().stream()
                        .anyMatch(invalid -> invalid.equalsIgnoreCase(category)));
    }

    @Override
    public String getName() {
        return "CheckExternalDebtsCompanyTypeRule";
    }
}
