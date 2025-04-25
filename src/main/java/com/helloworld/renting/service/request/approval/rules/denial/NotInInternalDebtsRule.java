package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import org.springframework.stereotype.Component;

@Component
public class NotInInternalDebtsRule extends DenialRule {
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        validate(rulesContextDto);
        return rulesContextDto.getNonpayments().isEmpty();
    }

    @Override
    public String getName() {
        return "Holder is not in internal debts";
    }
}
