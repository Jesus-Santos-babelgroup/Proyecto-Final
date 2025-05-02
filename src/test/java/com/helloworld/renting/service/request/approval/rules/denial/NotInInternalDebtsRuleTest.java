package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotInInternalDebtsRuleTest {

    private NotInInternalDebtsRule rule;

    @BeforeEach
    void setUp() {
        rule = new NotInInternalDebtsRule();
    }

    @Test
    void conditionMet_whenClientInInternalDebts_returnsFalse() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(1L);
        ctx.setClientNif("12345678A");
        ctx.setDebts(Collections.emptyList());
        ctx.setInformaRecords(Collections.emptyList());
        List<NonpaymentDto> nonpayments = List.of(new NonpaymentDto());
        ctx.setNonpayments(nonpayments);

        assertFalse(rule.conditionMet(ctx),
                "Si existe algún nonpayment, la regla debe devolver false");
    }

    @Test
    void conditionMet_whenClientNotInInternalDebts_returnsTrue() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(2L);
        ctx.setClientNif("87654321B");
        ctx.setDebts(Collections.emptyList());
        ctx.setInformaRecords(Collections.emptyList());
        ctx.setNonpayments(Collections.emptyList());

        assertTrue(rule.conditionMet(ctx),
                "Con lista de nonpayments vacía, la regla debe devolver true");
    }

    @Test
    void conditionMet_whenContextIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> rule.conditionMet(null),
                "Si el contexto es null, debe lanzarse NullPointerException");
    }

    @Test
    void conditionMet_whenClientNifBlank_throwsIllegalArgumentException() {
        RulesContextDto ctx = new RulesContextDto();
        ctx.setRequestId(3L);
        ctx.setClientNif("");
        ctx.setDebts(Collections.emptyList());
        ctx.setInformaRecords(Collections.emptyList());
        ctx.setNonpayments(Collections.emptyList());

        assertThrows(InvalidRulesContextDtoException.class,
                () -> rule.conditionMet(ctx),
                "Si el NIF está en formato inválido (vacío), debe lanzarse InvalidRulesContextDtoException");
    }
}
