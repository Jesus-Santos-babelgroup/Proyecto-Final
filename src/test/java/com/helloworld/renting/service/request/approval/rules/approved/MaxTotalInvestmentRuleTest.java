package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment.MaxTotalInvestmentProperties;
import com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment.MaxTotalInvestmentRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MaxTotalInvestmentRuleTest {

    private MaxTotalInvestmentRule sut;

    @BeforeEach
    void setUp() {
        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(BigDecimal.valueOf(80000));
        sut = new MaxTotalInvestmentRule(properties);
    }

    @Test
    void should_returnTrue_when_TotalInvestmentIsSmallerThanMaxTotalInvestment() {
        // Given
        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(50000));

        // When
        boolean result = sut.conditionMet(rulesContextDto);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_TotalInvestmentIsEqualToMaxTotalInvestment() {
        // Given
        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(80000));

        // When
        boolean result = sut.conditionMet(rulesContextDto);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_TotalInvestmentIsNull() {
        // Given
        String message = "Total Investment cannot be null";
        RulesContextDto rulesContextDto = new RulesContextDto();

        // When
        InvalidRulesContextDtoException exception = assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContextDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_TotalInvestmentIsNegative() {
        // Given
        String message = "Total Investment cannot be negative";
        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(-10000));

        // When
        InvalidRulesContextDtoException exception = assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContextDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_MaxTotalInvestmentIsNull() {
        // Given
        String message = "Max Total Investment cannot be null";

        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(null);
        sut = new MaxTotalInvestmentRule(properties);

        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(50000));

        // When
        InvalidRulesContextDtoException exception = assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContextDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_MaxTotalInvestmentIsNegative() {
        // Given
        String message = "Max Total Investment cannot be negative";

        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(BigDecimal.valueOf(-50));
        sut = new MaxTotalInvestmentRule(properties);

        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(50000));

        // When
        InvalidRulesContextDtoException exception = assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(rulesContextDto));

        // Then
        assertEquals(message, exception.getMessage());
    }
}
