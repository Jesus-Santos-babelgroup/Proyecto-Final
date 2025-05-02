package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.mapper.DebtMapper;
import com.helloworld.renting.mapper.MapStructDebt;
import com.helloworld.renting.service.request.approval.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class DebtAmountLessThanMonthlyQuoteTest {

    Rules sut;

    @Mock
    RentingRequestDto requestDto;
    @Mock
    ClientDto clientDto;
    @Mock
    DebtMapper debtMapper;
    @Mock
    MapStructDebt mapStructDebt;

    @BeforeEach
    void setUp() {
        sut = new DebtAmountLessThanMonthlyQuote(debtMapper, mapStructDebt);

        when(requestDto.getClient()).thenReturn(clientDto);
        when(clientDto.getNif()).thenReturn("12345678A");
        when(mapStructDebt.toDto(any(Debt.class))).thenAnswer(invocation -> {
            Debt deuda = invocation.getArgument(0);
            DebtDto dto = new DebtDto();
            dto.setId(deuda.getId());
            dto.setAmount(deuda.getAmount());
            return dto;
        });
    }

    @Test
    void should_returnTrue_when_debtIsLessThanMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(800.0));
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertTrue(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsGreaterThanMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(800));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(100.0));
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_returnFalse_when_debtIsEqualToMonthlyQuote() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(100));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(100.0));
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When
        boolean ruleState = sut.conditionMet(requestDto);

        // Then
        assertFalse(ruleState);
    }

    @Test
    void should_throwException_when_debtAmountIsNull() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        // debt.getAmount()==null
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(1000.0));
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When / Then
        assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(requestDto)
        );
    }

    @Test
    void should_throwException_when_debtAmountIsNegative() {
        // Given
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setAmount(BigDecimal.valueOf(-500));
        when(requestDto.getQuotaFinal()).thenReturn(BigDecimal.valueOf(1000.0));
        when(debtMapper.findDebtsByNif("12345678A")).thenReturn(List.of(debt));

        // When / Then
        assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(requestDto)
        );
    }

    @Test
    void should_throwException_when_monthlyQuotaIsNull() {
        // Given
        when(requestDto.getQuotaFinal()).thenReturn(null);

        // When / Then
        assertThrows(
                InvalidRentingRequestDtoException.class,
                () -> sut.conditionMet(requestDto)
        );
    }
}
