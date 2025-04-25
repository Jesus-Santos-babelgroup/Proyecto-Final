package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.service.request.approval.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // When

        // Then
    }

    @Test
    void should_returnFalse_when_debtIsGreaterThanMonthlyQuote() {
        // Given

        // When

        // Then
    }

    @Test
    void should_throwException_when_debtAmountIsNull() {

    }

    @Test
    void should_throwException_when_debtAmountIsNegative() {

    }

    @Test
    void should_throwException_when_debtFormatIsInvalid() {

    }

//     @Mock
//    DebtDto debt1;
//
//    @Mock
//    DebtDto debt2;
//
//    @BeforeEach
//    void setUp() {
//        sut = new DenialRuleDebtAmountLessThanMonthlyQuote();
//    }
//
//    @Test
//    void should_returnTrue_when_debtIsGreaterThanMonthlyQuote() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(1000.0);
//        when(debt1.getAmount()).thenReturn(new BigDecimal("800.00"));
//        when(debt2.getAmount()).thenReturn(new BigDecimal("500.00"));
//        when(rulesContext.getDebts()).thenReturn(List.of(debt1, debt2));
//
//        // When
//        boolean ruleState = sut.conditionMet(rulesContext);
//
//        // Then
//        assertTrue(ruleState);
//    }
//
//    @Test
//    void should_returnFalse_when_debtIsLessThanMonthlyQuote() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(2000.0);
//        when(debt1.getAmount()).thenReturn(new BigDecimal("300.00"));
//        when(debt2.getAmount()).thenReturn(new BigDecimal("400.00"));
//        when(rulesContext.getDebts()).thenReturn(List.of(debt1, debt2));
//
//        // When
//        boolean ruleState = sut.conditionMet(rulesContext);
//
//        // Then
//        assertFalse(ruleState);
//    }
//
//    @Test
//    void should_returnFalse_when_debtsListIsEmpty() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(2000.0);
//        when(rulesContext.getDebts()).thenReturn(List.of());
//
//        // When
//        boolean ruleState = sut.conditionMet(rulesContext);
//
//        // Then
//        assertFalse(ruleState); // empty debt list => not greater than quota
//    }
//
//    @Test
//    void should_throwException_when_debtAmountIsNull() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(1000.0);
//        when(debt1.getAmount()).thenReturn(null);
//        when(rulesContext.getDebts()).thenReturn(List.of(debt1));
//
//        // When / Then
//        assertThrows(NullPointerException.class, () -> sut.conditionMet(rulesContext));
//    }
//
//    @Test
//    void should_throwException_when_monthlyQuotaIsNull() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(null);
//        when(rulesContext.getDebts()).thenReturn(List.of());
//
//        // When / Then
//        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContext));
//    }
//
//    @Test
//    void should_throwException_when_debtListIsNull() {
//        // Given
//        when(rulesContext.getMonthly_quota()).thenReturn(1200.0);
//        when(rulesContext.getDebts()).thenReturn(null);
//
//        // When / Then
//        assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContext));
//    }
}