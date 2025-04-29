package com.helloworld.renting.service.request.approval.rules.denial.minagepermittedrule;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MinAgePermittedRule implements DenialRule {

    private MinAgePermittedProperties minAgePermittedProperties;
    private static final Logger logger = LoggerFactory.getLogger(MinAgePermittedRule.class);

    public MinAgePermittedRule(MinAgePermittedProperties minAgePermittedProperties) {
        this.minAgePermittedProperties = minAgePermittedProperties;
    }

    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        LocalDate birthDate = rentingRequestDto.getClient().getDateOfBirth();
        LocalDate today = LocalDate.now();

        if (birthDateIsNotValid(birthDate, today)) {
            logger.warn("Client birth date is not valid");
            throw new InvalidRentingRequestDtoException("Client birth date is not valid");
        } else {
            logger.debug("MinAgePermittedRule checked");
            return birthDate.isAfter(today.minusYears(minAgePermittedProperties.getMinimumAge()));
        }

    }

    public String getName() {
        return "MinAgePermittedRule";
    }

    private boolean birthDateIsNotValid(LocalDate birthDate, LocalDate today) {
        return birthDate == null || birthDate.isAfter(today);
    }

}