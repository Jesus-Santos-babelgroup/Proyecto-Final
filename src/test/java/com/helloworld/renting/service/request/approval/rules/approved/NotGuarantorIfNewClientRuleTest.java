package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.approved.notGuarantorIfNewClient.NotGuarantorIfNewClientMapper;
import com.helloworld.renting.service.request.approval.rules.approved.notGuarantorIfNewClient.NotGuarantorIfNewClientRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotGuarantorIfNewClientRuleTest {
    private NotGuarantorIfNewClientMapper mapper;
    private NotGuarantorIfNewClientRule rule;

    @BeforeEach
    void setUp() {
        mapper = mock(NotGuarantorIfNewClientMapper.class);
        rule = new NotGuarantorIfNewClientRule(mapper);
    }

    private RentingRequestDto createRequest(Long id, String nif) {
        ClientDto client = new ClientDto();
        client.setNif(nif);

        RentingRequestDto dto = new RentingRequestDto();
        dto.setId(id);
        dto.setClient(client);
        return dto;
    }

    @Test
    void testNewClientAndNotGuarantor() {
        RentingRequestDto dto = createRequest(1L, "12345678A");
        when(mapper.isNewClientByRequestId(1L)).thenReturn(true);
        when(mapper.hasBeenGuarantorInApprovedWithGuarantee("12345678A")).thenReturn(false);

        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testNewClientAndGuarantor() {
        RentingRequestDto dto = createRequest(2L, "12345678B");
        when(mapper.isNewClientByRequestId(2L)).thenReturn(true);
        when(mapper.hasBeenGuarantorInApprovedWithGuarantee("12345678B")).thenReturn(true);

        assertFalse(rule.conditionMet(dto));
    }

    @Test
    void testNotNewClientAndGuarantor() {
        RentingRequestDto dto = createRequest(3L, "12345678C");
        when(mapper.isNewClientByRequestId(3L)).thenReturn(false);
        when(mapper.hasBeenGuarantorInApprovedWithGuarantee("12345678C")).thenReturn(true);

        assertTrue(rule.conditionMet(dto));
    }

    @Test
    void testNotNewClientAndNotGuarantor() {
        RentingRequestDto dto = createRequest(4L, "12345678D");
        when(mapper.isNewClientByRequestId(4L)).thenReturn(false);
        when(mapper.hasBeenGuarantorInApprovedWithGuarantee("12345678D")).thenReturn(false);

        assertTrue(rule.conditionMet(dto));
    }
}