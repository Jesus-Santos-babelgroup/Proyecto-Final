package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
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
class DenialRuleDebtAmountLessThanMonthlyQuoteTest {

    Rules sut;
    @Mock
    RulesContextDto rulesContext;

    @BeforeEach
    void setUp() {
        sut = new DenialRuleDebtAmountLessThanMonthlyQuote();
    }

    @Test
    void should_returnTrue_when_debtIsLessThanMonthlyQuote() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(rulesContext.getMonthly_quota()).thenReturn(800.0);
        when(rulesContext.getDebts()).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(rulesContext);

        // Then
        assertTrue(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsGreaterThanMonthlyQuote() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(800));
        when(rulesContext.getMonthly_quota()).thenReturn(100.0);
        when(rulesContext.getDebts()).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(rulesContext);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsEqualToMonthlyQuote() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(rulesContext.getMonthly_quota()).thenReturn(100.0);
        when(rulesContext.getDebts()).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(rulesContext);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_throwException_when_debtAmountIsNull() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        when(rulesContext.getMonthly_quota()).thenReturn(1000.0);
        when(rulesContext.getDebts()).thenReturn(List.of(debt));

        // when / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContext));
    }

    @Test
    void should_throwException_when_debtAmountIsNegative() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(-500));
        when(rulesContext.getMonthly_quota()).thenReturn(1000.0);
        when(rulesContext.getDebts()).thenReturn(List.of(debt));

        // when / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContext));
    }

    @Test
    void should_throwException_when_monthlyQuotaIsNull() {
        // Given
        DebtDto debt = new DebtDto();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(500));
        when(rulesContext.getMonthly_quota()).thenReturn(null);

        // When / Then
        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContext));
    }
}