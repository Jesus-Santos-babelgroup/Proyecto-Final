/*
package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.DebtDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DebtLessThanMonthlyQuotaRuleTest {

    private DebtLessThanMonthlyQuotaRule rule;

    @BeforeEach
    void setUp() {
        rule = new DebtLessThanMonthlyQuotaRule();
    }

    @Test
    void testDebtLessThanMonthlyQuota() {
        RulesContextDto context = createContext(List.of(debt(100.0)), 200.0);
        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testDebtEqualToMonthlyQuota() {
        RulesContextDto context = createContext(List.of(debt(200.0)), 200.0);
        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testDebtGreaterThanMonthlyQuota() {
        RulesContextDto context = createContext(List.of(debt(300.0)), 200.0);
        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testNullDebtList() {
        RulesContextDto context = new RulesContextDto();
        context.setDebts(null);
        context.setMonthly_quota(200.0);
        assertTrue(rule.conditionMet(context)); // Consider 0.0 < 200.0
    }

    @Test
    void testEmptyDebtList() {
        RulesContextDto context = createContext(List.of(), 200.0);
        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testNegativeDebtAmount() {
        RulesContextDto context = createContext(List.of(debt(-50.0)), 200.0);
        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testNullMonthlyQuota() {
        RulesContextDto context = createContext(List.of(debt(100.0)), null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> rule.conditionMet(context));
        assertEquals("Monthly quota cannot be null", exception.getMessage());
    }

    @Test
    void testNegativeMonthlyQuota() {
        RulesContextDto context = createContext(List.of(debt(100.0)), -100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> rule.conditionMet(context));
        assertEquals("Monthly quota cannot be negative", exception.getMessage());
    }


    private RulesContextDto createContext(List<DebtDto> debts, Double quota) {
        RulesContextDto context = new RulesContextDto();
        context.setDebts(debts);
        context.setMonthly_quota(quota);
        return context;
    }

    private DebtDto debt(Double amount) {
        DebtDto dto = new DebtDto();
        dto.setAmount(BigDecimal.valueOf(amount));
        return dto;
    }
}*/
