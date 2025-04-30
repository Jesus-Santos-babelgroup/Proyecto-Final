package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears.NotRejectedInLast2YearsRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NotRejectedInLast2YearsRuleTest {

    private NotRejectedInLast2YearsRule notRejectedInLast2YearsRule = new NotRejectedInLast2YearsRule();
    private RulesContextDto rulesContext = new RulesContextDto();

    @Test
    void shouldReturnTrue_whenNotRejectedRecently() {
        rulesContext.setPreviouslyRejected(false);
        assertTrue(notRejectedInLast2YearsRule.conditionMet(rulesContext));
    }

    @Test
    void shouldReturnFalse_whenRejectedRecently() {
        rulesContext.setPreviouslyRejected(true);
        assertFalse(notRejectedInLast2YearsRule.conditionMet(rulesContext));
    }
}
