package com.helloworld.renting.service.request.approval.rules.approved.scoringBelowThresholdRule;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoringBelowThresholdRuleTest {

    private ScoringBelowThresholdProperties properties;
    private ScoringBelowThresholdRule rule;

    @BeforeEach
    void setUp() {
        properties = mock(ScoringBelowThresholdProperties.class);
        when(properties.getScoringThreshold()).thenReturn(5);
        when(properties.getMinScoring()).thenReturn(1);
        when(properties.getMaxScoring()).thenReturn(8);
        rule = new ScoringBelowThresholdRule(properties);
    }

    private RentingRequestDto createDto(Integer scoring) {
        ClientDto client = new ClientDto();
        client.setScoring(scoring);

        RentingRequestDto dto = new RentingRequestDto();
        dto.setClient(client);
        return dto;
    }

    @Test
    void testScoringExactlyAtThresholdShouldFail() {
        RentingRequestDto dto = createDto(5);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringJustBelowThresholdShouldPass() {
        RentingRequestDto dto = createDto(4);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringBelowMinRangeShouldFail() {
        RentingRequestDto dto = createDto(0);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringAboveMaxRangeShouldFail() {
        RentingRequestDto dto = createDto(9);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringWellWithinRangeButAboveThresholdShouldFail() {
        RentingRequestDto dto = createDto(6);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringNullShouldFail() {
        RentingRequestDto dto = createDto(null);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMinShouldPassIfBelowThreshold() {
        RentingRequestDto dto = createDto(1);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMaxBelowThresholdShouldPass() {
        RentingRequestDto dto = createDto(4);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMaxAndEqualToThresholdShouldFail() {
        RentingRequestDto dto = createDto(5);
        assertFalse(rule.conditionMet(dto));
    }
}