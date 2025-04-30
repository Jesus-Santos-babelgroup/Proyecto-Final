package com.helloworld.renting.service.request.approval.rules.approved.minimumSeniorityRule;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MinimumSeniorityRule implements ApprovedRule {

    private final MinimumSeniorityProperties seniorityRuleProperties;

    @Autowired
    public MinimumSeniorityRule(MinimumSeniorityProperties seniorityRuleProperties) {
        this.seniorityRuleProperties = seniorityRuleProperties;
    }

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        int seniority = LocalDate.now().getYear() - rulesContextDto.getEmploymentStartDate().getYear();
        if (seniority < 0) {
            throw new IllegalArgumentException("Employment start date cannot be in the future");
        }
        return seniority >= seniorityRuleProperties.getRequiredYears();
    }

    @Override
    public String getName() {
        return "SeniorityGreaterThan" + seniorityRuleProperties.getRequiredYears() + "Years";
    }
}
