package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalInvestmentSmallerThan80000RuleTest {

    private TotalInvestmentSmallerThan80000Rule sut;

    @BeforeEach
    void setUp() {
        sut = new TotalInvestmentSmallerThan80000Rule();
    }

    @Test
    void should_returnTrue_when_TotalInvestmentIsSmallerThan80000() {
        // Given
        RulesContextDto rulesContextDto = new RulesContextDto();
        rulesContextDto.setTotalInvestment(BigDecimal.valueOf(50000));

        // When
        boolean result = sut.conditionMet(rulesContextDto);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_TotalInvestmentIsEqualTo80000() {
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
}
