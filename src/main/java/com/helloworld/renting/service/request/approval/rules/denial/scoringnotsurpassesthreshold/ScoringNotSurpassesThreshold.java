package com.helloworld.renting.service.request.approval.rules.denial.scoringnotsurpassesthreshold;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ScoringNotSurpassesThreshold implements DenialRule {


    private ScoringNotSurpassesThresholdProperties properties;

    public ScoringNotSurpassesThreshold(ScoringNotSurpassesThresholdProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {
        Integer threshold = properties.getScoringThreshold();
        validate(requestDto);
        return requestDto.getClient().getScoring() < threshold;
    }

    @Override
    public String getName() {
        return "Holder scoring does not surpass threshold";
    }

    private void validate(RentingRequestDto requestDto) {
        Objects.requireNonNull(requestDto, "RentingRequestDto no puede ser null");

        if (requestDto.getClient().getScoring() == null) {
            throw new InvalidRentingRequestDtoException("Scoring no puede ser null.");
        } else if (requestDto.getClient().getScoring() < 0) {
            throw new InvalidRentingRequestDtoException("Scoring no puede ser negativo.");
        }
    }
}
