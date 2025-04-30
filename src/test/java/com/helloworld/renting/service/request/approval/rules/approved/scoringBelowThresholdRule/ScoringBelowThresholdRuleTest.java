package com.helloworld.renting.service.request.approval.rules.approved.scoringBelowThresholdRule;

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

    @Test
    void testScoringExactlyAtThresholdShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(5);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringJustBelowThresholdShouldPass() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(4);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringBelowMinRangeShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(0);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringAboveMaxRangeShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(9);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringWellWithinRangeButAboveThresholdShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(6);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringNullShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(null);
        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMinShouldPassIfBelowThreshold() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(1);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMaxBelowThresholdShouldPass() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(4);
        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testScoringAtMaxAndEqualToThresholdShouldFail() {
        RulesContextDto dto = new RulesContextDto();
        dto.setClientScoring(5);
        assertFalse(rule.conditionMet(dto));
    }
}