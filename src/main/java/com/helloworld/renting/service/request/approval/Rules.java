package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RulesContextDto;

public interface Rules {
    boolean conditionMet(RulesContextDto rulesContextDto);
}