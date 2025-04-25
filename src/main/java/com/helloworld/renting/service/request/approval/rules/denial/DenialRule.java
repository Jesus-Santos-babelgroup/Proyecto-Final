package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.service.request.approval.Rules;

import java.util.Objects;

public abstract class DenialRule implements Rules {
    protected void validate(RulesContextDto rulesContextDto) {
        Objects.requireNonNull(rulesContextDto, "RulesContextDto no puede ser null");

        if (rulesContextDto.getRequestId() == null) {
            throw new InvalidRulesContextDtoException("RequestId es obligatorios");
        } else if (rulesContextDto.getClientNif().isEmpty()) {
            throw new InvalidRulesContextDtoException("ClientNif no puede ser vacio");
        } else if (rulesContextDto.getClientScoring() == null) {
            throw new InvalidRulesContextDtoException("Scoring no puede ser null");
        }
    }
}