/*
package com.helloworld.renting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.service.client.EconomicDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EconomicDataController.class)
class EconomicDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EconomicDataService economicDataService;

    @Autowired
    private ObjectMapper objectMapper;

    private EconomicDataSelfEmployedDto selfEmployedDto;
    private EconomicDataEmployedDto employedDto;

    @BeforeEach
    void setup() {
        selfEmployedDto = new EconomicDataSelfEmployedDto();
        selfEmployedDto.setClientId(1L);
        selfEmployedDto.setGrossIncome(BigDecimal.valueOf(50000));
        selfEmployedDto.setNetIncome(BigDecimal.valueOf(30000));
        selfEmployedDto.setYearEntry(2023);

        employedDto = new EconomicDataEmployedDto();
        employedDto.setClientId(1L);
        employedDto.setCif("B12345678");
        employedDto.setGrossIncome(BigDecimal.valueOf(40000));
        employedDto.setNetIncome(BigDecimal.valueOf(25000));
        employedDto.setStartDate(LocalDate.of(2023, 1, 1));
        employedDto.setEndDate(LocalDate.of(2023, 12, 31));
        employedDto.setYearEntry(2023);
    }

    @Test
    void shouldCreateSelfEmployedData() throws Exception {
        Mockito.when(economicDataService.addEconomicDataSelfEmployed(any(), eq(1L)))
                .thenReturn(selfEmployedDto);

        mockMvc.perform(post("/api/clients/1/self_employed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(selfEmployedDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grossIncome").value("50000"))
                .andExpect(jsonPath("$.netIncome").value("30000"))
                .andExpect(jsonPath("$.yearEntry").value(2023));
    }

    @Test
    void shouldCreateEmployedData() throws Exception {
        Mockito.when(economicDataService.addEconomicDataEmployed(any(), eq(1L)))
                .thenReturn(employedDto);

        mockMvc.perform(post("/api/clients/1/employed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employedDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cif").value("B12345678"))
                .andExpect(jsonPath("$.grossIncome").value("40000"))
                .andExpect(jsonPath("$.netIncome").value("25000"))
                .andExpect(jsonPath("$.yearEntry").value(2023));
    }
}
*/
