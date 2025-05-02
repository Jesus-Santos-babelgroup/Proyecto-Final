package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.PreResultType;
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
    private ApprovedRule approvalRule1;
    @Mock
    private ApprovedRule approvalRule2;
    @Mock
    private DenialRule denialRule1;
    @Mock
    private DenialRule denialRule2;

    private RuleEvaluator evaluator;
    private RentingRequestDto dummyReq;

    @BeforeEach
    void setUp() {
        evaluator = new RuleEvaluator(
                List.of(approvalRule1, approvalRule2),
                List.of(denialRule1, denialRule2)
        );
        dummyReq = new RentingRequestDto();
    }

    @Test
    void evaluate_anyDenialRuleFails_returnsPredenied() {
        // Arrange
        when(denialRule1.conditionMet(dummyReq)).thenReturn(true);
        when(denialRule2.conditionMet(dummyReq)).thenReturn(false);

        // Act
        PreResultType result = evaluator.evaluate(dummyReq);

        // Assert
        assertEquals(PREDENIED, result);
    }

    @Test
    void evaluate_allDenialPass_and_allApprovalPass_returnsPreapproved() {
        // Arrange
        when(denialRule1.conditionMet(dummyReq)).thenReturn(true);
        when(denialRule2.conditionMet(dummyReq)).thenReturn(true);
        when(approvalRule1.conditionMet(dummyReq)).thenReturn(true);
        when(approvalRule2.conditionMet(dummyReq)).thenReturn(true);

        // Act
        PreResultType result = evaluator.evaluate(dummyReq);

        // Assert
        assertEquals(PREAPPROVED, result);
    }

    @Test
    void evaluate_allDenialPass_and_someApprovalFails_returnsNeedsReview() {
        // Arrange
        when(denialRule1.conditionMet(dummyReq)).thenReturn(true);
        when(denialRule2.conditionMet(dummyReq)).thenReturn(true);
        when(approvalRule1.conditionMet(dummyReq)).thenReturn(true);
        when(approvalRule2.conditionMet(dummyReq)).thenReturn(false);

        // Act
        PreResultType result = evaluator.evaluate(dummyReq);

        // Assert
        assertEquals(NEEDS_REVIEW, result);
    }
}
