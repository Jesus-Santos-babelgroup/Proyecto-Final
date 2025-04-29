package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.entities.PreResultType;
import com.helloworld.renting.mapper.RulesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ApprovalServiceTest {

    @Mock
    private RulesMapper rulesMapper;
    @Mock
    private RuleEvaluator ruleEvaluator;
    @InjectMocks
    private ApprovalService approvalService;

    private final Long REQUEST_ID = 42L;
    private RulesContextDto dummyCtx;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyCtx = new RulesContextDto();
    }

    @Test
    void evaluate_callsMapperAndEvaluator_returnsEvaluatorResult() {
        // Arrange
        when(rulesMapper.getRulesContext(REQUEST_ID)).thenReturn(dummyCtx);
        when(ruleEvaluator.evaluate(dummyCtx)).thenReturn(PreResultType.PREAPPROVED);

        // Act
        var result = approvalService.evaluate(REQUEST_ID);

        // Assert
        assertEquals(PreResultType.PREAPPROVED, result);
        verify(rulesMapper, times(1)).getRulesContext(REQUEST_ID);
        verify(ruleEvaluator, times(1)).evaluate(dummyCtx);
    }

    @Test
    void evaluate_propagatesNullPointerException_whenMapperReturnsNull() {
        // Arrange
        when(rulesMapper.getRulesContext(REQUEST_ID)).thenReturn(null);
        when(ruleEvaluator.evaluate(null)).thenThrow(new NullPointerException("ctx null"));

        // Act & Assert
        var ex = assertThrows(
                NullPointerException.class,
                () -> approvalService.evaluate(REQUEST_ID)
        );
        assertEquals("ctx null", ex.getMessage());
    }
}
