package com.helloworld.renting.service.request.approval.rules.approved.InformaDataApproval;

import com.helloworld.renting.dto.RentingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class InformaDataApprovalRuleTest {


    private InformaDataApprovalRule rule;
    private RentingRequestDto rentingRequestDto;
    private InformaDataApprovalMapper mapper;
    private InformaDataApprovalProperties properties;

    @BeforeEach
    public void setup() {
        mapper = mock(InformaDataApprovalMapper.class);
        properties.setLimit(BigDecimal.valueOf(150000));
        rule = new InformaDataApprovalRule(properties, mapper);
        rentingRequestDto = mock(RentingRequestDto.class);
    }

    int minYear = LocalDate.now().getYear() - 2;

    @Test
    void testInformaDataApprovalRuleValidData() {
        when(mapper.getCifByClientID(rentingRequestDto.getId())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(160000),
                BigDecimal.valueOf(155000),
                BigDecimal.valueOf(150000));
        when(mapper.findProfitLast3YearsByCif(mapper.getCifByClientID(rentingRequestDto.getId()), minYear))
                .thenReturn(listBD);

        assertTrue(rule.conditionMet(rentingRequestDto));
    }

    @Test
    void testInformaDataApprovalRuleValidDataOlderThanTwoYears() {
        when(mapper.getCifByClientID(rentingRequestDto.getId())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(160000));
        when(mapper.findProfitLast3YearsByCif(mapper.getCifByClientID(rentingRequestDto.getId()), minYear))
                .thenReturn(listBD);

        assertTrue(rule.conditionMet(rentingRequestDto));
    }

    @Test
    void testInformaDataApprovalRuleSelfEmployed() {
        when(mapper.getCifByClientID(rentingRequestDto.getId())).thenReturn(null);
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(160000),
                BigDecimal.valueOf(155000),
                BigDecimal.valueOf(150000));
        when(mapper.findProfitLast3YearsByCif(mapper.getCifByClientID(rentingRequestDto.getId()), minYear))
                .thenReturn(listBD);

        assertTrue(rule.conditionMet(rentingRequestDto));
    }

    @Test
    void testInformaDataApprovalRuleLessThan150k() {
        when(mapper.getCifByClientID(rentingRequestDto.getId())).thenReturn("B12345678");
        List<BigDecimal> listBD = List.of(
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(95000),
                BigDecimal.valueOf(150000));
        when(mapper.findProfitLast3YearsByCif(mapper.getCifByClientID(rentingRequestDto.getId()), minYear))
                .thenReturn(listBD);

        assertFalse(rule.conditionMet(rentingRequestDto));
    }

    @Test
    void testInformaDataApprovalRuleNoInformaRecord() {
        when(mapper.getCifByClientID(rentingRequestDto.getId())).thenReturn("B12345678");
        when(mapper.findProfitLast3YearsByCif(mapper.getCifByClientID(rentingRequestDto.getId()), minYear))
                .thenReturn(Collections.emptyList());

        assertFalse(rule.conditionMet(rentingRequestDto));
    }
}
