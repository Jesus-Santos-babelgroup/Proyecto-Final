package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpanishNationalityRuleTest {

    private SpanishNationalityRule sut;

    @BeforeEach
    void setUp() {
        sut = new SpanishNationalityRule();
    }

    @Test
    void should_returnTrue_when_clientIsSpanish() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientNationality("Spain");

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_clientIsNotSpanish() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientNationality("Poland");

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_nationalityIsNull() {
        // Given
        String message = "Client nationality is null";
        RulesContextDto context = new RulesContextDto();

        // When
        AttributeException exception = assertThrows(AttributeException.class, () -> sut.conditionMet(context));

        // Then
        assertEquals(message, exception.getMessage());
    }
}