package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.mapper.RulesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotGuarantorIfNewClientRuleTest {
    private RulesMapper rulesMapper;
    private NotGuarantorIfNewClientRule rule;

    @BeforeEach
    void setUp() {
        rulesMapper = mock(RulesMapper.class);
        rule = new NotGuarantorIfNewClientRule(rulesMapper);
    }

    @Test
    void testNewClientAndNotGuarantor() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(1L);
        ctx.setClientNif("12345678A");

        when(rulesMapper.isNewClient(1L)).thenReturn(true);
        when(rulesMapper.hasBeenGuarantorInApprovedWithGuarantee("12345678A")).thenReturn(false);

        assertTrue(rule.conditionMet(ctx));
    }

    @Test
    void testNewClientAndGuarantor() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(2L);
        ctx.setClientNif("12345678B");

        when(rulesMapper.isNewClient(2L)).thenReturn(true);
        when(rulesMapper.hasBeenGuarantorInApprovedWithGuarantee("12345678B")).thenReturn(true);

        assertFalse(rule.conditionMet(ctx));
    }

    @Test
    void testNotNewClientAndGuarantor() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(3L);
        ctx.setClientNif("12345678C");

        when(rulesMapper.isNewClient(3L)).thenReturn(false);
        when(rulesMapper.hasBeenGuarantorInApprovedWithGuarantee("12345678C")).thenReturn(true);

        assertTrue(rule.conditionMet(ctx));
    }

    @Test
    void testNotNewClientAndNotGuarantor() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(4L);
        ctx.setClientNif("12345678D");

        when(rulesMapper.isNewClient(4L)).thenReturn(false);
        when(rulesMapper.hasBeenGuarantorInApprovedWithGuarantee("12345678D")).thenReturn(false);

        assertTrue(rule.conditionMet(ctx));
    }
}