package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import com.helloworld.renting.service.request.approval.rules.denial.DenialRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.helloworld.renting.entities.PreResultType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleEvaluatorTest {

    @Mock
    ApprovedRule approvalRule1;
    @Mock
    ApprovedRule approvalRule2;
    @Mock
    DenialRule denialRule1;
    @Mock
    DenialRule denialRule2;

    private RuleEvaluator evaluator;
    private RulesContextDto dummyCtx;

    @BeforeEach
    void setUp() {
        evaluator = new RuleEvaluator(
                List.of(approvalRule1, approvalRule2),
                List.of(denialRule1, denialRule2)
        );
        dummyCtx = new RulesContextDto();
    }

    @Test
    void evaluate_whenAnyDenialRuleFalse_returnsPredenied() {
        // Arrange
        when(denialRule1.conditionMet(dummyCtx)).thenReturn(false);

        // Act
        var result = evaluator.evaluate(dummyCtx);

        // Assert
        assertEquals(PREDENIED, result);
    }

    @Test
    void evaluate_whenNoDenialAndAllApprovalsTrue_returnsPreapproved() {
        // Arrange
        when(denialRule1.conditionMet(dummyCtx)).thenReturn(true);
        when(denialRule2.conditionMet(dummyCtx)).thenReturn(true);
        when(approvalRule1.conditionMet(dummyCtx)).thenReturn(true);
        when(approvalRule2.conditionMet(dummyCtx)).thenReturn(true);

        // Act
        var result = evaluator.evaluate(dummyCtx);

        // Assert
        assertEquals(PREAPPROVED, result);
    }

    @Test
    void evaluate_whenNoDenialAndSomeApprovalFalse_returnsNeedsReview() {
        // Arrange
        when(denialRule1.conditionMet(dummyCtx)).thenReturn(true);
        when(denialRule2.conditionMet(dummyCtx)).thenReturn(true);
        when(approvalRule1.conditionMet(dummyCtx)).thenReturn(true);
        when(approvalRule2.conditionMet(dummyCtx)).thenReturn(false);

        // Act
        var result = evaluator.evaluate(dummyCtx);

        // Assert
        assertEquals(NEEDS_REVIEW, result);
    }
}
