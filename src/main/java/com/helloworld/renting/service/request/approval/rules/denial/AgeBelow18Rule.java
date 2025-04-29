package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AgeBelow18Rule implements DenialRule {

    private static final Logger logger = LoggerFactory.getLogger(AgeBelow18Rule.class);

    public boolean conditionMet(RulesContextDto context) {
        LocalDate birthDate = context.getClientBirthDate();
        LocalDate today = LocalDate.now();

        if (birthDateIsNotValid(birthDate, today)) {
            logger.warn("Client birth date is not valid");
            throw new InvalidRulesContextDtoException("Client birth date is not valid");
        } else {
            logger.debug("AgeBelow18Rule checked");
            return birthDate.isAfter(today.minusYears(18));
        }

    }

    public String getName() {
        return "AgeBelow18Rule";
    }

    private boolean birthDateIsNotValid(LocalDate birthDate, LocalDate today) {
        return birthDate == null || birthDate.isAfter(today);
    }

}
