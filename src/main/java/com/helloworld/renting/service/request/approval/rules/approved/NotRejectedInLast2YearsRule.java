package com.helloworld.renting.service.request.approval.rules.approved;

import org.springframework.stereotype.Component;

@Component
public class NotRejectedInLast2YearsRule implements ApprovedRule {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {

        return !rulesContextDto.isPreviouslyRejected();
    }

    @Override
    public String getName() {
        return "NotRejectedInLast2YearsRule";
    }
}
