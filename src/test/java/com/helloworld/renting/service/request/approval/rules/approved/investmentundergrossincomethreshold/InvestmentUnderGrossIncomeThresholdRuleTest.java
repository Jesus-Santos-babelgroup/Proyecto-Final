package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvestmentUnderGrossIncomeThresholdRuleTest {

    private InvestmentUnderGrossIncomeThresholdProperties properties;
    private InvestmentUnderGrossIncomeThresholdMapper mapper;
    private InvestmentUnderGrossIncomeThresholdRule sut;

    @BeforeEach
    void setUp() {
        properties = mock(InvestmentUnderGrossIncomeThresholdProperties.class);
        when(properties.getMultiplier()).thenReturn(3);
        mapper = mock(InvestmentUnderGrossIncomeThresholdMapper.class);
        sut = new InvestmentUnderGrossIncomeThresholdRule(mapper, properties);
    }

    @Test
    void investmentLessThanGrossIncomeThreshold() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(new BigDecimal("24000"));
        data1.setYearEntry(2021);

        EconomicDataSelfEmployedDto data2 = new EconomicDataSelfEmployedDto();
        data2.setGrossIncome(new BigDecimal("25000"));
        data2.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1, data2);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("70000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void investmentGreaterThanGrossIncomeThreshold() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(new BigDecimal("24000"));
        data1.setYearEntry(2021);

        EconomicDataSelfEmployedDto data2 = new EconomicDataSelfEmployedDto();
        data2.setGrossIncome(new BigDecimal("20000"));
        data2.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1, data2);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("70000"));

        assertFalse(sut.conditionMet(dto));
    }

    @Test
    void investmentEgualToGrossIncomeThreshold() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(new BigDecimal("24000"));
        data1.setYearEntry(2021);

        EconomicDataSelfEmployedDto data2 = new EconomicDataSelfEmployedDto();
        data2.setGrossIncome(new BigDecimal("25000"));
        data2.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1, data2);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("75000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void isNotSelfEmployed() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        List<EconomicDataSelfEmployedDto> economicData = List.of();

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("75000"));

        assertTrue(sut.conditionMet(dto));
    }

    @Test
    void investmentIsNull() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data2 = new EconomicDataSelfEmployedDto();
        data2.setGrossIncome(new BigDecimal("25000"));
        data2.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data2);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(null);

        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(dto));
    }

    @Test
    void investmentIsNegative() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(new BigDecimal("25000"));
        data1.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("-1000"));

        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(dto));
    }

    @Test
    void grossIncomeIsNegative() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(new BigDecimal("-25000"));
        data1.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("75000"));

        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(dto));
    }

    @Test
    void grossIncomeIsNull() {
        RentingRequestDto dto = new RentingRequestDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        dto.setClient(client);
        dto.setId(100L);

        EconomicDataSelfEmployedDto data1 = new EconomicDataSelfEmployedDto();
        data1.setGrossIncome(null);
        data1.setYearEntry(2023);

        List<EconomicDataSelfEmployedDto> economicData = List.of(data1);

        when(mapper.findByClientId(1L)).thenReturn(economicData);
        when(mapper.findTotalInvestment(100L)).thenReturn(new BigDecimal("75000"));

        assertThrows(InvalidRentingRequestDtoException.class, () -> sut.conditionMet(dto));
    }

}