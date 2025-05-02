package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.PreResultType;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {
    private final RuleEvaluator ruleEvaluator;

    public ApprovalService(RuleEvaluator ruleEvaluator) {
        this.ruleEvaluator = ruleEvaluator;
    }

    public PreResultType evaluate(RentingRequestDto rentingRequestDto) {
        return ruleEvaluator.evaluate(rentingRequestDto);
    }
}