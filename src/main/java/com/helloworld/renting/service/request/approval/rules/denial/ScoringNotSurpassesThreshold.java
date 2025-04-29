package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ScoringNotSurpassesThreshold implements DenialRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        validate(rulesContextDto);
        return rulesContextDto.getClientScoring() < 6;
    }

    @Override
    public String getName() {
        return "Holder scoring does not surpass threshold";
    }

    private void validate(RulesContextDto rulesContextDto) {
        Objects.requireNonNull(rulesContextDto, "RulesContextDto no puede ser null");

        if (rulesContextDto.getClientScoring() == null) {
            throw new InvalidRulesContextDtoException("Scoring no puede ser null.");
        } else if (rulesContextDto.getClientScoring() < 0) {
            throw new InvalidRulesContextDtoException("Scoring no puede ser negativo.");
        }
    }
}
