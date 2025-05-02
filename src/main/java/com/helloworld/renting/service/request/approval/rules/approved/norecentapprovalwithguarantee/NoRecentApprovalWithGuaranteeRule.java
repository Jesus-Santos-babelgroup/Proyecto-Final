package com.helloworld.renting.service.request.approval.rules.approved.norecentapprovalwithguarantee;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NoRecentApprovalWithGuaranteeRule implements ApprovedRule {

    private final NoRecentApprovalWithGuaranteeMapper mapper;

    private final NoRecentApprovalWithGuaranteeProperties properties;

    public NoRecentApprovalWithGuaranteeRule(NoRecentApprovalWithGuaranteeMapper mapper, NoRecentApprovalWithGuaranteeProperties properties) {
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        long clientId = rentingRequestDto.getClient().getId();
        LocalDate fromDate = LocalDate.now().minusYears(properties.getYears());

        int approvals = mapper.countPreviousApprovalsWithGuarantees(clientId, fromDate);

        return approvals == 0;
    }

    @Override
    public String getName() {
        return "NoRecentApprovalWithGuarantee";
    }
}
