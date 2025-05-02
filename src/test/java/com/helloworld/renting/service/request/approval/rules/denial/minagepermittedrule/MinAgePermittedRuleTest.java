package com.helloworld.renting.service.request.approval.rules.denial.minagepermittedrule;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MinAgePermittedRuleTest {

    private MinAgePermittedProperties minAgePermittedProperties;
    private MinAgePermittedRule sut;

    @BeforeEach
    void setUp() {
        minAgePermittedProperties = new MinAgePermittedProperties();
        minAgePermittedProperties.setMinimumAge(18);
        sut = new MinAgePermittedRule(minAgePermittedProperties);
    }

    @Test
    void should_returnTrue_when_ageIsBelowMinimum() {
        // Given
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        clientDto.setDateOfBirth(LocalDate.of(2020, 1, 1));
        rentingRequestDto.setClient(clientDto);

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_ageIsOverMinimum() {
        // Given
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        clientDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        rentingRequestDto.setClient(clientDto);

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_birthDateIsNull() {
        // Given
        String message = "Client birth date is not valid";
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        rentingRequestDto.setClient(clientDto);

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_raiseException_when_birthDateIsFuture() {
        // Given
        String message = "Client birth date is not valid";
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        clientDto.setDateOfBirth(LocalDate.of(9999, 12, 31));
        rentingRequestDto.setClient(clientDto);

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

}