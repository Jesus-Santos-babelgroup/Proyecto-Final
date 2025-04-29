package com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotInInternalDebtsRuleTest {

    @Mock
    private NotInInternalDebtsMapper mapper;

    private NotInInternalDebtsRule rule;

    @BeforeEach
    void setUp() {
        rule = new NotInInternalDebtsRule(mapper);
    }

    @Test
    void conditionMet_whenClientHasNonpayments_returnsFalse() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(100L);
        dto.setClient(client);

        when(mapper.findByClientId(100L))
                .thenReturn(List.of(new NonpaymentDto(), new NonpaymentDto()));

        // Act
        boolean result = rule.conditionMet(dto);

        // Assert
        assertFalse(result,
                "Si existe al menos un nonpayment, la regla debe devolver false");
    }

    @Test
    void conditionMet_whenClientHasNoNonpayments_returnsTrue() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(200L);
        dto.setClient(client);

        when(mapper.findByClientId(200L))
                .thenReturn(List.of()); // lista vacía

        // Act
        boolean result = rule.conditionMet(dto);

        // Assert
        assertTrue(result,
                "Con lista de nonpayments vacía, la regla debe devolver true");
    }

    @Test
    void conditionMet_whenDtoIsNull_throwsInvalidRentingRequestDtoException() {
        // Act & Assert
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> rule.conditionMet(null),
                "Si el DTO es null, debe lanzarse InvalidRentingRequestDtoException"
        );
        assertEquals("RentingRequestDto no puede ser null", ex.getMessage());
    }

    @Test
    void conditionMet_whenClientIsNull_throwsInvalidRentingRequestDtoException() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        dto.setClient(null);

        // Act & Assert
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> rule.conditionMet(dto),
                "Si el client es null, debe lanzarse InvalidRentingRequestDtoException"
        );
        assertEquals("Client no puede ser null", ex.getMessage());
    }

    @Test
    void conditionMet_whenClientIdIsNull_throwsInvalidRentingRequestDtoException() {
        // Arrange
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(null);
        dto.setClient(client);

        // Act & Assert
        InvalidClientDtoException ex = assertThrows(
                InvalidClientDtoException.class,
                () -> rule.conditionMet(dto),
                "Si client.id es null, debe lanzarse InvalidRentingRequestDtoException"
        );
        assertEquals("Client.id es obligatorio", ex.getMessage());
    }
}
