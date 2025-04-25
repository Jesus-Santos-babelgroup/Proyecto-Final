package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    private void validate(RulesContextDto rulesContextDto) {
        Objects.requireNonNull(rulesContextDto, "RulesContextDto no puede ser null");
        if (rulesContextDto.getRequestId() == null) {
            throw new InvalidRulesContextDtoException("RequestId es obligatorios");
        } else if (rulesContextDto.getClientNif().isEmpty()) {
            throw new InvalidRulesContextDtoException("ClientNif no puede ser vacio");
        }
    }
}
