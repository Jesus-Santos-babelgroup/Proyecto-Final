package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;

public class ScoringBelow5Rule extends ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        return rulesContextDto.getClientScoring()<5;
    }

    @Override
    public String getName() {
        return "scoringBelow5Rule";
    }
}