package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.service.request.approval.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringNotSurpassesThresholdTest {

    Rules sut;
    @Mock
    RentingRequestDto requestDto;
    @Value("${rules.denial-rule.scoring-threshold}")
    Integer scoringThreshold;
    Integer valueBelowThreshold;
    Integer valueAboveThreshold;

    @BeforeEach
    void setUp() {
        sut = new ScoringNotSurpassesThreshold();
        valueBelowThreshold = scoringThreshold - 1;
        valueAboveThreshold = scoringThreshold + 1;
    }

    @Test
    void should_returnTrue_when_conditionNotMet() {
        // Given
        when(requestDto.getClient().getScoring()).thenReturn(valueBelowThreshold);

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertTrue(ruleState);
    }

    @Test
    void should_returnFalse_when_conditionMet() {
        // Given
        when(requestDto.getClient().getScoring()).thenReturn(scoringThreshold);

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_throwException_when_scoreIsNull() {
        // Given
        when(requestDto.getClient().getScoring()).thenReturn(null);

        // When / Then
        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(requestDto));
    }

    @Test
    void should_throwException_when_scoreIsNegative() {
        // Given
        when(requestDto.getClient().getScoring()).thenReturn(-3);

        // When / Then
        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(requestDto));
    }
}