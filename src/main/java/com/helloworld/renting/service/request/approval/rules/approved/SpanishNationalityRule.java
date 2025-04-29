package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpanishNationalityRule implements ApprovedRule {

    Logger logger = LoggerFactory.getLogger(SpanishNationalityRule.class);

    public boolean conditionMet(RulesContextDto context) {
        if (context.getClientNationality() == null) {
            logger.warn("Client nationality is null");
            throw new InvalidRulesContextDtoException("Client nationality is null");
        } else {
            logger.debug("SpanishNationalityRule checked");
            return context.getClientNationality().equalsIgnoreCase("Spain");
        }
    }

    public String getName() {
        return "SpanishNationalityRule";
    }

}
