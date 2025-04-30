package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.service.request.approval.rules.approved.minimumSeniorityRule.MinimumSeniorityRule;
import com.helloworld.renting.service.request.approval.rules.approved.minimumSeniorityRule.MinimumSeniorityProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MinimumSeniorityRuleTest {

    private MinimumSeniorityRule rule;
    private MinimumSeniorityProperties properties;

    @BeforeEach
    void setUp() {
        properties = new MinimumSeniorityProperties();
        properties.setRequiredYears(3);
        rule = new MinimumSeniorityRule(properties);
    }

    @Test
    void testSeniorityExactly3Years() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(LocalDate.now().minusYears(3));
        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testSeniorityLessThan3Years() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(LocalDate.now().minusYears(2));
        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testSeniorityMoreThan3Years() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(LocalDate.now().minusYears(5));
        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testEmploymentStartDateInFuture() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(LocalDate.now().plusYears(1));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> rule.conditionMet(context));
        assertEquals("Employment start date cannot be in the future", exception.getMessage());
    }

    @Test
    void testEmploymentStartDateIsToday() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(LocalDate.now());
        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testEmploymentStartDateIsNull() {
        RulesContextDto context = new RulesContextDto();
        context.setEmploymentStartDate(null);
        assertThrows(NullPointerException.class, () -> rule.conditionMet(context));
    }
}
