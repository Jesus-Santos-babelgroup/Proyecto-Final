package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpanishNationalityRule implements ApprovedRule {

    private static final String SPAIN_ISOA2 = "ES";
    Logger logger = LoggerFactory.getLogger(SpanishNationalityRule.class);

    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        String nationality = rentingRequestDto.getClient().getCountry().getIsoA2();

        if (nationality == null) {
            logger.warn("Client nationality is null");
            throw new InvalidRentingRequestDtoException("Client nationality is null");
        } else {
            logger.debug("SpanishNationalityRule checked");
            return nationality.equalsIgnoreCase(SPAIN_ISOA2);
        }
    }

    public String getName() {
        return "SpanishNationalityRule";
    }

}
