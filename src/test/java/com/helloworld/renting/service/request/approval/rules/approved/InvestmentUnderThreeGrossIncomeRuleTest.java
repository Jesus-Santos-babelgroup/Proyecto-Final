package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentUnderThreeGrossIncomeRuleTest {

    private InvestmentUnderThreeGrossIncomeRule rule;

    @BeforeEach
    void setUp() {
        rule = new InvestmentUnderThreeGrossIncomeRule();
    }

    @Test
    void investmentLessThanThreeTimesGrossIncome() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("30000"));
        dto.setTotalInvestment(new BigDecimal("80000"));

        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void investmentEgualToThreeTimesGrossIncome() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("25000"));
        dto.setTotalInvestment(new BigDecimal("75000"));

        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void investmentGreaterThanThreeTimesGrossIncome() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("20000"));
        dto.setTotalInvestment(new BigDecimal("70000"));

        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void isNotSelfEmployed() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(null);
        dto.setTotalInvestment(new BigDecimal("70000"));

        assertTrue(rule.conditionMet(dto));
    }
}