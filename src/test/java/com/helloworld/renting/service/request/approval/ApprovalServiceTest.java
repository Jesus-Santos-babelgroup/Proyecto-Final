package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.PreResultType;
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
    private RuleEvaluator ruleEvaluator;

    @InjectMocks
    private ApprovalService approvalService;

    private RentingRequestDto dummyRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyRequest = new RentingRequestDto();
    }

    @Test
    void evaluate_delegatesToRuleEvaluator_andReturnsResult() {
        // Arrange
        when(ruleEvaluator.evaluate(dummyRequest))
                .thenReturn(PreResultType.PREAPPROVED);

        // Act
        PreResultType result = approvalService.evaluate(dummyRequest);

        // Assert
        assertEquals(PreResultType.PREAPPROVED, result);
        verify(ruleEvaluator, times(1)).evaluate(dummyRequest);
    }

    @Test
    void evaluate_propagatesException_fromRuleEvaluator() {
        // Arrange
        when(ruleEvaluator.evaluate(null))
                .thenThrow(new NullPointerException("dto null"));

        // Act & Assert
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> approvalService.evaluate(null)
        );
        assertEquals("dto null", ex.getMessage());
    }
}
