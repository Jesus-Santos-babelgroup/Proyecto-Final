package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpanishNationalityRule extends ApprovedRule {

    Logger logger = LoggerFactory.getLogger(SpanishNationalityRule.class);

    public boolean conditionMet(RulesContextDto context) {
        if (context.getClientNationality() == null) {
            logger.warn("Client nationality is null");
            throw new AttributeException("Client nationality is null");
        } else {
            logger.debug("SpanishNationalityRule checked");
            return context.getClientNationality().equalsIgnoreCase("Spain");
        }
    }

    public String getName() {
        return "SpanishNationalityRule";
    }

}
