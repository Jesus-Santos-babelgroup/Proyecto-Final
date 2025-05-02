package com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NotRejectedInLastYearsRuleTest {

    private NotRejectedInLastYearsMapper mapper;
    private NotRejectedInLastYearsProperties properties;
    private NotRejectedInLastYearsRule rule;

    @BeforeEach
    void setUp() {
        mapper = mock(NotRejectedInLastYearsMapper.class);
        properties = new NotRejectedInLastYearsProperties();
        properties.setYears(2);
        rule = new NotRejectedInLastYearsRule(mapper, properties);
    }

    private RentingRequestDto buildRequestWithClientId(Long clientId) {
        ClientDto client = new ClientDto();
        client.setId(clientId);
        RentingRequestDto request = new RentingRequestDto();
        request.setClient(client);
        return request;
    }

    @Test
    void returnsTrueWhenNoRejectedRequestsFound() {
        Long clientId = 1L;
        RentingRequestDto request = buildRequestWithClientId(clientId);

        when(mapper.countRejectedRequestsInLastYears(clientId, 2)).thenReturn(0);

        assertTrue(rule.conditionMet(request));
    }

    @Test
    void returnsFalseWhenRejectedRequestsExist() {
        Long clientId = 2L;
        RentingRequestDto request = buildRequestWithClientId(clientId);

        when(mapper.countRejectedRequestsInLastYears(clientId, 2)).thenReturn(3);

        assertFalse(rule.conditionMet(request));
    }

}
