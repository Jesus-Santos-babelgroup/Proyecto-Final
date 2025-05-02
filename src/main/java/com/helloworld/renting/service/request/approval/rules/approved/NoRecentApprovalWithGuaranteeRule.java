package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import org.springframework.stereotype.Component;

@Component
public class NoRecentApprovalWithGuaranteeRule implements ApprovedRule {
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        return !rulesContextDto.isPreviouslyApprovedWithGuarantee();
    }

    @Override
    public String getName() {
        return "NoRecentApprovalWithGuarantee";
    }
}
