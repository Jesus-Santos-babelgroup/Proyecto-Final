package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckExternalDebtsCompanyTypeRuleTest {

    private CheckExternalDebtsCompanyTypeRule rule;
    private RulesContextDto context;

    @BeforeEach
    void setUp() {
        rule = new CheckExternalDebtsCompanyTypeRule();
        context = new RulesContextDto();
    }

    @Test
    void testRuleName() {
        assertEquals("Comprobar deudas externas que no sean tipo 'Renting' ni 'Financiera'", rule.getName());
    }

    @Test
    void testConditionMet_NoDebts() {
        context.setDebts(Collections.emptyList());
        assertTrue(rule.conditionMet(context), "Should return true when there are no debts");
    }

    @Test
    void testConditionMet_DebtsNotRentingOrFinanciera() {
        List<DebtDto> debts = Arrays.asList(
                createDebt("BANCO", "Bank debt"),
                createDebt("TELCO", "Telecom debt"),
                createDebt("OTRO", "Other debt")
        );
        context.setDebts(debts);
        assertTrue(rule.conditionMet(context), "Should return true when no debts are RENTING or FINANCIERA");
    }

    @Test
    void testConditionMet_DebtWithRenting() {
        List<DebtDto> debts = Arrays.asList(
                createDebt("BANCO", "Bank debt"),
                createDebt("RENTING", "Car leasing"),
                createDebt("OTRO", "Other debt")
        );
        context.setDebts(debts);
        assertFalse(rule.conditionMet(context), "Should return false when at least one debt is RENTING");
    }

    @Test
    void testConditionMet_DebtWithFinanciera() {
        List<DebtDto> debts = Arrays.asList(
                createDebt("BANCO", "Bank debt"),
                createDebt("FINANCIERA", "Personal loan"),
                createDebt("OTRO", "Other debt")
        );
        context.setDebts(debts);
        assertFalse(rule.conditionMet(context), "Should return false when at least one debt is FINANCIERA");
    }

    @Test
    void testConditionMet_DebtWithFinancieraCaseInsensitive() {
        List<DebtDto> debts = Arrays.asList(
                createDebt("financiera", "Personal loan"),
                createDebt("FiNaNcIeRa", "Another loan"),
                createDebt("RENTing", "Car leasing")
        );
        context.setDebts(debts);
        assertFalse(rule.conditionMet(context), "Should be case insensitive for RENTING/FINANCIERA");
    }

    @Test
    void testConditionMet_MixedDebts() {
        List<DebtDto> debts = Arrays.asList(
                createDebt("RENTING", "Car leasing"),
                createDebt("FINANCIERA", "Personal loan"),
                createDebt("BANCO", "Bank debt")
        );
        context.setDebts(debts);
        assertFalse(rule.conditionMet(context), "Should return false when both RENTING and FINANCIERA debts exist");
    }

    @Test
    void testConditionMet_NullDebtList() {
        context.setDebts(null);
        assertTrue(rule.conditionMet(context), "Should handle null debt list gracefully");
    }

    @Test
    void testConditionMet_DebtWithNullCategory() {
        List<DebtDto> debts = Arrays.asList(
                createDebt(null, "Unknown debt"),
                createDebt("BANCO", "Bank debt")
        );
        context.setDebts(debts);
        assertTrue(rule.conditionMet(context), "Should handle null category gracefully");
    }

    private DebtDto createDebt(String category, String description) {
        DebtDto debt = new DebtDto();
        debt.setCategoryCompany(category);
        return debt;
    }
}