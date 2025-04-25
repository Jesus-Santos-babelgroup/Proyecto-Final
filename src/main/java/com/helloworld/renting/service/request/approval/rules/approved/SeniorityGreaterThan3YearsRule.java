package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeniorityGreaterThan3YearsRule extends ApprovedRule{
    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        int seniority = LocalDate.now().getYear() - rulesContextDto.getEmploymentStartDate().getYear();
        return seniority <= 3;
    }

    @Override
    public String getName() {
        return "SeniorityGreaterThan3Years";
    }
}
