package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.service.request.approval.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DenialRule2Test {

    Rules sut;
    @Mock
    Client client;

    @BeforeEach
    void setUp() {
        sut = new DenialRule2();
    }

    @Test
    void should_returnTrue_when_conditionMet() {
        // Given
        when(client.getScoring()).thenReturn(6);

        // When
        boolean ruleState = sut.conditionMet(client);

        // Then
        assertTrue(ruleState);
    }

    @Test
    void should_returnFalse_when_conditionNotMet() {
        // Given
        when(client.getScoring()).thenReturn(5);

        // When
        boolean ruleState = sut.conditionMet(client);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_throwException_when_scoreIsNull() {
        // Given
        when(client.getScoring()).thenReturn(null);

        // When / Then
        assertThrows(AttributeException.class, () -> sut.conditionMet(client));
    }

    @Test
    void should_throwException_when_scoreIsNegative() {
        // Given
        when(client.getScoring()).thenReturn(-3);

        // When / Then
        assertThrows(AttributeException.class, () -> sut.conditionMet(client));
    }

    @Test
    void should_invalidateRequest_when_userScoreIncreasesAboveThreshold() {
        // Given
        // When
        // Then
    }
}