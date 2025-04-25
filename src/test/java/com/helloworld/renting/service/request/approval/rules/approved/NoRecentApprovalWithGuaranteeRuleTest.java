package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoRecentApprovalWithGuaranteeRuleTest {

    private NoRecentApprovalWithGuaranteeRule rule;

    @BeforeEach
    void setUp() {
        rule = new NoRecentApprovalWithGuaranteeRule();
    }

    @Test
    void NotPreviouslyApprovedWithGuarantee() {
        RulesContextDto context = new RulesContextDto();
        context.setPreviouslyApprovedWithGuarantee(false);

        assertTrue(rule.conditionMet(context));
    }

    @Test
    void PreviouslyApprovedWithGuarantee() {
        RulesContextDto context = new RulesContextDto();
        context.setPreviouslyApprovedWithGuarantee(true);

        assertFalse(rule.conditionMet(context));
    }
}