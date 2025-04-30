package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.mapper.RulesMapper;
import org.springframework.stereotype.Component;

@Component
public class NotGuarantorIfNewClientRule implements ApprovedRule {
    private final RulesMapper mapper;

    public NotGuarantorIfNewClientRule(RulesMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        boolean isNew = mapper.isNewClient(rulesContextDto.getRequestId());
        boolean hasBeenGuarantor = mapper.hasBeenGuarantorInApprovedWithGuarantee(rulesContextDto.getClientNif());
        return !(isNew && hasBeenGuarantor);
    }

    @Override
    public String getName() {
        return "NotGuarantorIfNewClientRule";
    }
}
