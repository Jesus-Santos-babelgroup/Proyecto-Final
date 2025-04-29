package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.mapper.DebtMapper;
import com.helloworld.renting.service.request.approval.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebtAmountLessThanMonthlyQuoteTest {

    Rules sut;
    @Mock
    RentingRequestDto requestDto;
    @Mock
    DebtMapper debtMapper;

    @BeforeEach
    void setUp() {
        sut = new DebtAmountLessThanMonthlyQuote(debtMapper);
    }

    @Test
    void should_returnTrue_when_debtIsLessThanMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(800.0));
        when(requestDto.getClient().getNif()).thenReturn("12345678A");
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertTrue(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsGreaterThanMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(800));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(100.0));
        when(requestDto.getClient().getNif()).thenReturn("12345678A");
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsEqualToMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(100.0));
        when(requestDto.getClient().getNif()).thenReturn("12345678A");
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_throwException_when_debtAmountIsNull() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(1000.0));
        when(requestDto.getClient().getNif()).thenReturn("12345678A");
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // when / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(requestDto));
    }

    @Test
    void should_throwException_when_debtAmountIsNegative() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(-500));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(1000.0));
        when(requestDto.getClient().getNif()).thenReturn("12345678A");
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // when / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(requestDto));
    }

    @Test
    void should_throwException_when_monthlyQuotaIsNull() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(500));
        when(requestDto.getQuotaFinal()).thenReturn(null);

        // When / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(requestDto));
    }
}