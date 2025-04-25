package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.service.request.approval.Rules;
import org.springframework.stereotype.Component;

@Component
public class DenialRuleScoringNotSurpassesThreshold implements Rules {

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        validate(rulesContextDto);
        return rulesContextDto.getClientScoring() < 6;
    }

    @Override
    public String getName() {
        return "Holder scoring does not surpass threshold";
    }
}
