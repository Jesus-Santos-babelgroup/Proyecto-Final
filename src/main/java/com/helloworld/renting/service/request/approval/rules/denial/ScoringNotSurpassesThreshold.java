package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ScoringNotSurpassesThreshold implements DenialRule {

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {
        validate(requestDto);
        return requestDto.getClient().getScoring() < 6;
    }

    @Override
    public String getName() {
        return "Holder scoring does not surpass threshold";
    }

    private void validate(RentingRequestDto requestDto) {
        Objects.requireNonNull(requestDto, "RulesContextDto no puede ser null");

        if (requestDto.getClient().getScoring() == null) {
            throw new InvalidRentingRequestDtoException("Scoring no puede ser null.");
        } else if (requestDto.getClient().getScoring() < 0) {
            throw new InvalidRentingRequestDtoException("Scoring no puede ser negativo.");
        }
    }
}
