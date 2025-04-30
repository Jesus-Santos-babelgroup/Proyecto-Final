package com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidMappedDataException;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
        validateClientId(clientId);
        int years = rulesProperties.getYears();
        int rejectedCount = mapper.countRejectedRequestsInLastYears(clientId, years);
        validateRejectedCount(rejectedCount);
        return rejectedCount == 0;
    }

    void validateRejectedCount(int investment) {
        if (investment < 0) {
            throw new InvalidMappedDataException("Investment cannot be negative");
        }
    }

    void validateClientId(Long clientId) {
        if (clientId == null) {
            throw new InvalidClientDtoException("Client ID cannot be null");
        }
    }

    @Override
    public String getName() {
        return "NotRejectedInLastYearsRule";
    }
}
