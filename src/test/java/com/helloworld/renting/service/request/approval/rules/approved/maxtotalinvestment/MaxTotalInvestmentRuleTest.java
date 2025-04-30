package com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MaxTotalInvestmentRuleTest {

    private MaxTotalInvestmentRule sut;
    private MaxTotalInvestmentMapper mapper;
    private RentingRequestDto rentingRequestDto;

    @BeforeEach
    void setUp() {
        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(BigDecimal.valueOf(80000));

        sut = new MaxTotalInvestmentRule(properties, mapper);
        rentingRequestDto = mock(RentingRequestDto.class);
    }

    @Test
    public void should_returnTrue_when_TotalInvestmentIsSmallerThanMaxTotalInvestment() {
        // Given
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        //rentingRequestDto.setTotalInvestment(BigDecimal.valueOf(50000));
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("50000"));

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    public void should_returnFalse_when_TotalInvestmentIsEqualToMaxTotalInvestment() {
        // Given
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        //rentingRequestDto.setTotalInvestment(BigDecimal.valueOf(80000));
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("80000"));

        // When
        boolean result = sut.conditionMet(rentingRequestDto);

        // Then
        assertFalse(result);
    }

    @Test
    public void should_raiseException_when_TotalInvestmentIsNull() {
        // Given
        String message = "Total Investment cannot be null";
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(null);

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void should_raiseException_when_TotalInvestmentIsNegative() {
        // Given
        String message = "Total Investment cannot be negative";
        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        //rentingRequestDto.setTotalInvestment(BigDecimal.valueOf(-10000));
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("-10000"));

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void should_raiseException_when_MaxTotalInvestmentIsNull() {
        // Given
        String message = "Max Total Investment cannot be null";

        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(null);
        sut = new MaxTotalInvestmentRule(properties, mapper);

        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        //rentingRequestDto.setTotalInvestment(BigDecimal.valueOf(50000));
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("50000"));

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void should_raiseException_when_MaxTotalInvestmentIsNegative() {
        // Given
        String message = "Max Total Investment cannot be negative";

        MaxTotalInvestmentProperties properties = new MaxTotalInvestmentProperties();
        properties.setLimit(BigDecimal.valueOf(-50));
        sut = new MaxTotalInvestmentRule(properties, mapper);

        RentingRequestDto rentingRequestDto = new RentingRequestDto();
        //rentingRequestDto.setTotalInvestment(BigDecimal.valueOf(50000));
        when(mapper.getTotalInvestment(rentingRequestDto.getId())).thenReturn(new BigDecimal("50000"));

        // When
        InvalidRentingRequestDtoException exception = assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(rentingRequestDto));

        // Then
        assertEquals(message, exception.getMessage());
    }
}
