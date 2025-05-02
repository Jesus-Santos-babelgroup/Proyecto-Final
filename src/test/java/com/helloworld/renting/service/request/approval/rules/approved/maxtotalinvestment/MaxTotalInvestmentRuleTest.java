package com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaxTotalInvestmentRuleTest {

    @Mock
    private MaxTotalInvestmentMapper mapper;

    private MaxTotalInvestmentRule sut;

    @BeforeEach
    void setUp() {
        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(BigDecimal.valueOf(80000));
        sut = new MaxTotalInvestmentRule(properties, mapper);
    }

    @Test
    void should_returnTrue_when_TotalInvestmentIsSmallerThanMaxTotalInvestment() {
        // Given
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(new BigDecimal("50000"));

        // When
        boolean result = sut.conditionMet(dto);

        // Then
        assertTrue(result);
    }

    @Test
    void should_returnFalse_when_TotalInvestmentIsEqualToMaxTotalInvestment() {
        // Given
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(new BigDecimal("80000"));

        // When
        boolean result = sut.conditionMet(dto);

        // Then
        assertFalse(result);
    }

    @Test
    void should_raiseException_when_TotalInvestmentIsNull() {
        // Given
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(null);

        // When / Then
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(dto)
        );

        // Then
        assertEquals("Total Investment cannot be null", ex.getMessage());
    }

    @Test
    void should_raiseException_when_TotalInvestmentIsNegative() {
        // Given
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(new BigDecimal("-10000"));

        // When / Then
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(dto)
        );

        // Then
        assertEquals("Total Investment cannot be negative", ex.getMessage());
    }

    @Test
    void should_raiseException_when_MaxTotalInvestmentIsNull() {
        // Given
        MaxTotalInvestmentProperties props = new MaxTotalInvestmentProperties();
        props.setLimit(null);
        sut = new MaxTotalInvestmentRule(props, mapper);
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(new BigDecimal("50000"));

        // When / Then
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(dto)
        );

        // Then
        assertEquals("Max Total Investment cannot be null", ex.getMessage());
    }

    @Test
    void should_raiseException_when_MaxTotalInvestmentIsNegative() {
        // Given
        MaxTotalInvestmentProperties props = new MaxTotalInvestmentProperties();
        props.setLimit(BigDecimal.valueOf(-50));
        sut = new MaxTotalInvestmentRule(props, mapper);
        RentingRequestDto dto = new RentingRequestDto();
        when(mapper.getTotalInvestment(dto.getId())).thenReturn(new BigDecimal("50000"));

        // When / Then
        InvalidRentingRequestDtoException ex = assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(dto)
        );

        // Then
        assertEquals("Max Total Investment cannot be negative", ex.getMessage());
    }
}
