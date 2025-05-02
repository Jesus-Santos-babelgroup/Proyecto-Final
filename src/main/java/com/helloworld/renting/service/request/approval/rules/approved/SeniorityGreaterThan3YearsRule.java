/*
package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SeniorityGreaterThan3YearsRule implements ApprovedRule {
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        int seniority = LocalDate.now().getYear() - rulesContextDto.getEmploymentStartDate().getYear();
        if (seniority < 0)
            throw new IllegalArgumentException("Employment start date cannot be in the future");
        return seniority >= 3;
    }

    @Override
    public String getName() {
        return "SeniorityGreaterThan3Years";
    }
}
*/
