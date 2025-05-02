package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.entities.PreResultType;
import com.helloworld.renting.mapper.RulesMapper;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {
    private final RulesMapper rulesMapper;
    private final RuleEvaluator ruleEvaluator;

    public ApprovalService(RulesMapper rulesMapper, RuleEvaluator ruleEvaluator) {
        this.rulesMapper = rulesMapper;
        this.ruleEvaluator = ruleEvaluator;
    }

    public PreResultType evaluate(Long requestId) {
        RulesContextDto rulesContextDto = rulesMapper.getRulesContext(requestId);
        return ruleEvaluator.evaluate(rulesContextDto);
    }
}