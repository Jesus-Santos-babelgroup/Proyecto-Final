package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AgeBelow18RuleTest {

    private AgeBelow18Rule sut;

    @BeforeEach
    void setUp() {
        sut = new AgeBelow18Rule();
    }

    @Test
    void should_returnTrue_when_ageIsBelow18() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(2010,1,1));

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_ageIsOver18() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(2000,1,1));

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_birthDateIsNull() {
        // Given
        String message = "Client birth date is not valid";
        RulesContextDto context = new RulesContextDto();

        // When
        AttributeException exception = assertThrows(AttributeException.class, () -> sut.conditionMet(context));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_birthDateIsFuture() {
        // Given
        String message = "Client birth date is not valid";
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(9999,12,31));

        // When
        InvalidRulesContextDtoException exception = assertThrows(InvalidRulesContextDtoException.class, () -> sut.conditionMet(context));

        // Then
        assertEquals(message, exception.getMessage());
    }

}