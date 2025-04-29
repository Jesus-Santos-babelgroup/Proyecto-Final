package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule.NotInInternalDebtsMapper;
import com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule.NotInInternalDebtsRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class NotInInternalDebtsRuleTest {

    @Mock
    private NotInInternalDebtsMapper nonpaymentMapper;
    private NotInInternalDebtsRule rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rule = new NotInInternalDebtsRule(nonpaymentMapper);
    }

    @Test
    void conditionMet_whenClientInInternalDebts_returnsFalse() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(10L);
        dto.setClient(client);

        when(nonpaymentMapper.findByClientId(10L))
                .thenReturn(List.of(new NonpaymentDto(), new NonpaymentDto()));

        // Act
        boolean result = rule.conditionMet(dto);

        // Assert
        assertFalse(result,
                "Si existe al menos un nonpayment, la regla debe devolver false");
    }

    @Test
    void conditionMet_whenClientNotInInternalDebts_returnsTrue() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(20L);
        dto.setClient(client);

        when(nonpaymentMapper.findByClientId(20L))
                .thenReturn(List.of()); // sin impagos

        // Act
        boolean result = rule.conditionMet(dto);

        // Assert
        assertTrue(result,
                "Con lista de nonpayments vacía, la regla debe devolver true");
    }

    @Test
    void conditionMet_whenDtoIsNull_throwsNullPointerException() {
        // Arrange / Act / Assert
        assertThrows(
                NullPointerException.class,
                () -> rule.conditionMet(null),
                "Si el DTO es null, debe lanzarse NullPointerException"
        );
    }

    @Test
    void conditionMet_whenClientIsNull_throwsNullPointerException() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        dto.setClient(null);

        // Act / Assert
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> rule.conditionMet(dto),
                "Si el client es null, debe lanzarse NullPointerException"
        );
        assertEquals("client no puede ser null", ex.getMessage());
    }

    @Test
    void conditionMet_whenClientIdIsNull_throwsInvalidRulesContextDtoException() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(null);
        dto.setClient(client);

        // Act / Assert
        InvalidRulesContextDtoException ex = assertThrows(
                InvalidRulesContextDtoException.class,
                () -> rule.conditionMet(dto),
                "Si client.id es null, debe lanzarse InvalidRulesContextDtoException"
        );
        assertEquals("Client.id es obligatorio", ex.getMessage());
    }
}
