package com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule;

import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotInInternalDebtsRule implements DenialRule {

    private final NotInInternalDebtsMapper mapper;

    public NotInInternalDebtsRule(NotInInternalDebtsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto dto) {
        if (dto == null) {
            throw new InvalidRentingRequestDtoException("RentingRequestDto no puede ser null");
        }

        if (dto.getClient() == null) {
            throw new InvalidRentingRequestDtoException("Client no puede ser null");
        }

        Long clientId = dto.getClient().getId();

        if (clientId == null) {
            throw new InvalidClientDtoException("Client.id es obligatorio");
        }
        
        List<NonpaymentDto> nonpayments = mapper.findByClientId(clientId);
        return nonpayments.isEmpty();
    }

    @Override
    public String getName() {
        return "Holder is not in internal debts";
    }
}
