package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpanishNationalityRuleTest {

    private SpanishNationalityRule sut;

    @BeforeEach
    void setUp() {
        sut = new SpanishNationalityRule();
    }

    @Test
    void should_returnTrue_when_clientIsSpanish() {
        // Given
        CountryDto countryDto = new CountryDto();
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        countryDto.setIsoA2("ES");
        clientDto.setCountry(countryDto);
        rentingRequestDto.setClient(clientDto);

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_clientIsNotSpanish() {
        // Given
        CountryDto countryDto = new CountryDto();
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        countryDto.setIsoA2("AR");
        clientDto.setCountry(countryDto);
        rentingRequestDto.setClient(clientDto);

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_nationalityIsNull() {
        // Given
        String message = "Client nationality is null";
        CountryDto countryDto = new CountryDto();
        ClientDto clientDto = new ClientDto();
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        clientDto.setCountry(countryDto);
        rentingRequestDto.setClient(clientDto);

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }
}