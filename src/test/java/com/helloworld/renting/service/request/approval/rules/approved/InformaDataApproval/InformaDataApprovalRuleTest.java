package com.helloworld.renting.service.request.approval.rules.approved.InformaDataApproval;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InformaDataApprovalRuleTest {

    private InformaDataApprovalRule rule;
    private RentingRequestDto rentingRequestDto;
    private InformaDataApprovalMapper mapper;
    private InformaDataApprovalProperties properties;
    private ClientDto clientDto;

    private final int minYear = LocalDate.now().getYear() - 2;

    @BeforeEach
    public void setup() {
        clientDto = mock(ClientDto.class);
        when(clientDto.getId()).thenReturn(1L);

        properties = new InformaDataApprovalProperties();
        properties.setLimit(BigDecimal.valueOf(150000));

        mapper = mock(InformaDataApprovalMapper.class);

        rule = new InformaDataApprovalRule(properties, mapper);

        rentingRequestDto = mock(RentingRequestDto.class);
        when(rentingRequestDto.getClient()).thenReturn(clientDto);
    }

    @Test
    void testInformaDataApprovalRuleValidData() {
        // Given
        when(mapper.getCifByClientID(anyLong())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(160000),
                BigDecimal.valueOf(155000),
                BigDecimal.valueOf(150000)
        );
        when(mapper.findProfitLast3YearsByCif("B12345678", minYear))
                .thenReturn(listBD);

        // When
        boolean result = rule.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    void testInformaDataApprovalRuleValidDataOlderThanTwoYears() {
        // Given
        when(mapper.getCifByClientID(anyLong())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(160000)
        );
        when(mapper.findProfitLast3YearsByCif("B12345678", minYear))
                .thenReturn(listBD);

        // When
        boolean result = rule.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    void testInformaDataApprovalRuleSelfEmployed() {
        // Given
        when(mapper.getCifByClientID(anyLong())).thenReturn(null);

        // When
        boolean result = rule.conditionMet(rentingRequestDto);

        // Then
        assertTrue(result);
    }

    @Test
    void testInformaDataApprovalRuleLessThan150k() {
        // Given
        when(mapper.getCifByClientID(anyLong())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(95000),
                BigDecimal.valueOf(150000)
        );
        when(mapper.findProfitLast3YearsByCif("B12345678", minYear))
                .thenReturn(listBD);

        // When
        boolean result = rule.conditionMet(rentingRequestDto);

        // Then
        assertFalse(result);
    }

    @Test
    void testInformaDataApprovalRuleNoInformaRecord() {
        // Given
        when(mapper.getCifByClientID(anyLong())).thenReturn("B12345678");
        when(mapper.findProfitLast3YearsByCif("B12345678", minYear))
                .thenReturn(Collections.emptyList());

        // When
        boolean result = rule.conditionMet(rentingRequestDto);

        // Then
        assertFalse(result);
    }
}
