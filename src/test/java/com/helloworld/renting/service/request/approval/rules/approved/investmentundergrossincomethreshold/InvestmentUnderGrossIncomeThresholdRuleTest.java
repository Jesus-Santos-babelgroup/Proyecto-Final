package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvestmentUnderGrossIncomeThresholdRuleTest {

    private InvestmentUnderGrossIncomeThresholdProperties properties;
    private InvestmentUnderGrossIncomeThresholdRule sut;

    @BeforeEach
    void setUp() {
        properties = mock(InvestmentUnderGrossIncomeThresholdProperties.class);
        when(properties.getMultiplier()).thenReturn(3);
        sut = new InvestmentUnderGrossIncomeThresholdRule(properties);
    }

    @Test
    void investmentLessThanGrossIncomeThreshold() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("25000"));
        dto.setTotalInvestment(new BigDecimal("75000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void investmentGreaterThanGrossIncomeThreshold() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("20000"));
        dto.setTotalInvestment(new BigDecimal("70000"));

        assertFalse(sut.conditionMet(dto));
    }

    @Test
    void investmentEgualToGrossIncomeThreshold() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("25000"));
        dto.setTotalInvestment(new BigDecimal("75000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void isNotSelfEmployed() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(null);
        dto.setTotalInvestment(new BigDecimal("70000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void investmentIsNull() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("1000"));
        dto.setTotalInvestment(null);

        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(dto));
    }

    @Test
    void investmentIsNegative() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("1000"));
        dto.setTotalInvestment(new BigDecimal("-1000"));

        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(dto));
    }

    @Test
    void grossIncomeIsNegative() {
        RulesContextDto dto = new RulesContextDto();
        dto.setGrossIncomeSelfEmployed(new BigDecimal("-1000"));
        dto.setTotalInvestment(new BigDecimal("1000"));

        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(dto));
    }

}