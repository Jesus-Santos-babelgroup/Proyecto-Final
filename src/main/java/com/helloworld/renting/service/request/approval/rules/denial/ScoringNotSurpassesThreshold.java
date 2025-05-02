package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ScoringNotSurpassesThreshold implements DenialRule {

    @Value("${rules.denial-rule.scoring-threshold}")
    private int threshold;

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {
        validate(requestDto);
        return requestDto.getClient().getScoring() < threshold;
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
