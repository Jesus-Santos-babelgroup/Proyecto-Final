package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;

public class NotInInternalDebtsRule extends ApprovedRule {
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        return false;
    }

    @Override
    public String getName() {
        return "";
    }
}