package com.helloworld.renting.service.request.approval.rules.approved.norecentapprovalwithguarantee;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoRecentApprovalWithGuaranteeRuleTest {

    private NoRecentApprovalWithGuaranteeMapper mapper;
    private NoRecentApprovalWithGuaranteeProperties properties;
    private NoRecentApprovalWithGuaranteeRule sut;

    @BeforeEach
    void setUp() {
        properties = mock(NoRecentApprovalWithGuaranteeProperties.class);
        when(properties.getYears()).thenReturn(2);
        mapper = mock(NoRecentApprovalWithGuaranteeMapper.class);

        sut = new NoRecentApprovalWithGuaranteeRule(mapper, properties);
    }

    @Test
    void approvalsInLastYears() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);

        when(mapper.countPreviousApprovalsWithGuarantees(1L, LocalDate.now().minusYears(2))).thenReturn(1);

        boolean result = sut.conditionMet(dto);

        assertFalse(result);
    }

    @Test
    void noApprovalsInLastYears() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);

        when(mapper.countPreviousApprovalsWithGuarantees(1L, LocalDate.now().minusYears(2))).thenReturn(0);

        boolean result = sut.conditionMet(dto);

        assertTrue(result);
    }
    
}