package com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidMappedDataException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

@Component
public class NotRejectedInLastYearsRule implements ApprovedRule {

    private final NotRejectedInLastYearsProperties rulesProperties;

    private final NotRejectedInLastYearsMapper mapper;

    public NotRejectedInLastYearsRule(NotRejectedInLastYearsMapper mapper, NotRejectedInLastYearsProperties rulesProperties) {
        this.mapper = mapper;
        this.rulesProperties = rulesProperties;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        Long clientId = rentingRequestDto.getClient().getId();
        if (clientId == null) {
            throw new InvalidClientDtoException("Client ID cannot be null");
        }
        int years = rulesProperties.getYears();
        int rejectedCount = mapper.countRejectedRequestsInLastYears(clientId, years);
        if (rejectedCount < 0) {
            throw new InvalidMappedDataException("Rejected count cannot be negative");
        }
        return rejectedCount == 0;
    }

    @Override
    public String getName() {
        return "NotRejectedInLast2YearsRule";
    }
}
