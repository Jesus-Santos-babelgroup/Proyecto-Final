package com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet;

import com.helloworld.renting.dto.RentingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvestmentExceedsNetRuleTest {
    private InvestmentExceedsNetRule rule;
    private RentingRequestDto rentingRequestDto;
    private InvestmentExceedsNetMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = mock(InvestmentExceedsNetMapper.class);

        rule = new InvestmentExceedsNetRule(mapper);
        rentingRequestDto = mock(RentingRequestDto.class);
        when(rentingRequestDto.getId()).thenReturn(1L);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentIsLessThanNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("20000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("10000"));
        when(mapper.getInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("25000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentEqualsNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("15000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("5000"));
        when(mapper.getInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse_whenInvestmentIsGreaterThanNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("10000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("5000"));
        when(mapper.getInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertFalse(result);
    }
}
