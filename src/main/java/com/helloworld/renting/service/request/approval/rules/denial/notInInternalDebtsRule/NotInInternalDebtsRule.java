package com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule;

import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class NotInInternalDebtsRule implements DenialRule {

    private final NotInInternalDebtsMapper mapper;

    public NotInInternalDebtsRule(NotInInternalDebtsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto dto) {
        Objects.requireNonNull(dto, "dto no puede ser null");
        Objects.requireNonNull(dto.getClient(), "client no puede ser null");
        Long clientId = dto.getClient().getId();
        if (clientId == null) {
            throw new InvalidRulesContextDtoException("Client.id es obligatorio");
        }
        List<NonpaymentDto> nonpayments = mapper.findByClientId(clientId);
        return nonpayments.isEmpty();
    }

    @Override
    public String getName() {
        return "Holder is not in internal debts";
    }
}
