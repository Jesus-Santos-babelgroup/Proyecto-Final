package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.entities.PreResultType;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEvaluator {
    private final List<ApprovedRule> approvalRules;
    private final List<DenialRule> denialRules;

    public RuleEvaluator(List<ApprovedRule> approvalRules,
                         List<DenialRule> denialRules) {
        this.approvalRules = approvalRules;
        this.denialRules = denialRules;
    }


    public PreResultType evaluate(RulesContextDto rulesContextDto) {
        if (denialRules.stream().anyMatch(r -> !r.conditionMet(rulesContextDto))) {
            return PreResultType.PREDENIED;
        }
        if (approvalRules.stream().allMatch(r -> r.conditionMet(rulesContextDto))) {
            return PreResultType.PREAPPROVED;
        }
        return PreResultType.NEEDS_REVIEW;
    }
}