package com.helloworld.renting.service.request.approval.rules.approved.notGuarantorIfNewClient;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.mapper.RulesMapper;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

@Component
public class NotGuarantorIfNewClientRule implements ApprovedRule {
    private final NotGuarantorIfNewClientMapper mapper;

    public NotGuarantorIfNewClientRule(NotGuarantorIfNewClientMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        boolean isNew = mapper.isNewClientByRequestId(rentingRequestDto.getId());
        boolean hasBeenGuarantor = mapper.hasBeenGuarantorInApprovedWithGuarantee(
                rentingRequestDto.getClient().getNif());

        return !(isNew && hasBeenGuarantor);
    }

    @Override
    public String getName() {
        return "NotGuarantorIfNewClientRule";
    }
}
