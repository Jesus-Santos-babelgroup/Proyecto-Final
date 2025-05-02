/*
package com.helloworld.renting.service.request.approval.rules.denial;


import com.helloworld.renting.exceptions.attributes.AttributeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AgeClientPlusContractTermTest {
    private AgeClientPlusContractTerm sut;

    @BeforeEach
    void setUp() {
        sut = new AgeClientPlusContractTerm();
    }

    @Test
    void should_returnTrue_when_agePlusContractTerm_isBelow80() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(1990, 1, 1)); // Cliente con 30 años
        context.setContractingDate(LocalDate.of(2020, 1, 1)); // Fecha de contrato
        context.setContractingDate(LocalDate.ofEpochDay(45));  // Plazo de 45 años

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_agePlusContractTerm_isEqualTo80() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(1990, 1, 1)); // Cliente con 30 años
        context.setContractingDate(LocalDate.of(2020, 1, 1)); // Fecha de contrato
        context.setContractingDate(LocalDate.ofEpochDay(50));  // Plazo de 50 años

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertFalse(result);
    }

    @Test
    void should_returnFalse_when_agePlusContractTerm_isAbove80() {
        // Given
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(1990, 1, 1)); // Cliente con 30 años
        context.setContractingDate(LocalDate.of(2020, 1, 1)); // Fecha de contrato
        context.setContractingDate(LocalDate.ofEpochDay(55));  // Plazo de 55 años

        // When
        boolean result = sut.conditionMet(context);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_birthDateIsNull() {
        // Given
        String message = "Client birth date or contract date is not valid";
        RulesContextDto context = new RulesContextDto();
        context.setContractingDate(LocalDate.of(2020, 1, 1)); // Fecha de contrato válida

        // When
        AttributeException exception = assertThrows(AttributeException.class, () -> sut.conditionMet(context));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_contractDateIsNull() {
        // Given
        String message = "Client birth date or contract date is not valid";
        RulesContextDto context = new RulesContextDto();
        context.setClientBirthDate(LocalDate.of(1990, 1, 1)); // Fecha de nacimiento válida

        // When
        AttributeException exception = assertThrows(AttributeException.class, () -> sut.conditionMet(context));

        // Then
        assertEquals(message, exception.getMessage());
    }


}
*/
