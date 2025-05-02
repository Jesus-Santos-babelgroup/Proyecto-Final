package com.helloworld.renting.service.request.approval.rules.approved.scoringBelowThresholdRule;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

@Component
public class ScoringBelowThresholdRule implements ApprovedRule {

    private final ScoringBelowThresholdProperties scoringBelowThresholdProperties;

    public ScoringBelowThresholdRule(ScoringBelowThresholdProperties scoringBelowThresholdProperties) {
        this.scoringBelowThresholdProperties = scoringBelowThresholdProperties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        Integer scoring = rentingRequestDto.getClient().getScoring();
        int threshold = scoringBelowThresholdProperties.getScoringThreshold();
        if (scoring == null) {
            return false;
        }

        boolean isScoringInRange = scoringBelowThresholdProperties.getMinScoring() <= scoring
                && scoring <= scoringBelowThresholdProperties.getMaxScoring();

        return isScoringInRange && scoring < threshold;
    }

    @Override
    public String getName() {
        return "ScoringBelow5Rule";
    }
}