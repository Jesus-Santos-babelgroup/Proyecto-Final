package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet.InvestmentExceedsNetMapper;
import com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet.InvestmentExceedsNetRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvestmentExceedsnetRuleTest {
    private InvestmentExceedsNetRule rule;
    private RentingRequestDto rentingRequestDto;
    private InvestmentExceedsNetMapper mapper;

    @BeforeEach
    public void setup() {
        rule = new InvestmentExceedsNetRule(mapper);
        rentingRequestDto = mock(RentingRequestDto.class);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentIsLessThanNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("20000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("10000"));
        when(rentingRequestDto.getQuotaFinal()).thenReturn(new BigDecimal("25000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenInvestmentEqualsNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("15000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("5000"));
        when(rentingRequestDto.getQuotaFinal()).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse_whenInvestmentIsGreaterThanNetIncome() {
        when(mapper.getNetIncomeEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("10000"));
        when(mapper.getNetIncomeSelfEmployed(rentingRequestDto.getId())).thenReturn(new BigDecimal("5000"));
        when(rentingRequestDto.getQuotaFinal()).thenReturn(new BigDecimal("20000"));

        boolean result = rule.conditionMet(rentingRequestDto);
        assertFalse(result);
    }
}
