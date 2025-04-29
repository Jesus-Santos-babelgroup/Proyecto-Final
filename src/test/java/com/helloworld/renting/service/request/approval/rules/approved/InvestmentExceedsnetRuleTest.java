package com.helloworld.renting.service.request.approval.rules.approved;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvestmentExceedsnetRuleTest {
    private InvestmentExceedsNetRule rule;
    private RulesContextDto rulesContext;

    @BeforeEach
    public void setup() {
        rule = new InvestmentExceedsNetRule();
        rulesContext = mock(RulesContextDto.class);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentIsLessThanNetIncome() {
        when(rulesContext.getNetIncomeEmployed()).thenReturn(new BigDecimal("20000"));
        when(rulesContext.getNetIncomeSelfEmployed()).thenReturn(new BigDecimal("10000"));
        when(rulesContext.getTotalInvestment()).thenReturn(new BigDecimal("25000"));

        boolean result = rule.conditionMet(rulesContext);
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentEqualsNetIncome() {
        when(rulesContext.getNetIncomeEmployed()).thenReturn(new BigDecimal("15000"));
        when(rulesContext.getNetIncomeSelfEmployed()).thenReturn(new BigDecimal("5000"));
        when(rulesContext.getTotalInvestment()).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rulesContext);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse_whenInvestmentIsGreaterThanNetIncome() {
        when(rulesContext.getNetIncomeEmployed()).thenReturn(new BigDecimal("10000"));
        when(rulesContext.getNetIncomeSelfEmployed()).thenReturn(new BigDecimal("5000"));
        when(rulesContext.getTotalInvestment()).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rulesContext);
        assertFalse(result);
    }
}
