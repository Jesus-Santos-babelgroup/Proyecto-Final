package com.helloworld.renting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private EconomicDataController sut;

    @BeforeEach
    void setup() {
        sut = new EconomicDataController(economicDataService);
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

    @Test
    void should_returnNoContent_when_economicDataEmployedIsDeleted() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        doNothing().when(economicDataService).deleteEconomicDataEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnNotFound_when_deleteEconomicDataEmployedThrowsException() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        doThrow(EconomicDataNotFoundException.class).when(economicDataService).deleteEconomicDataEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnInternalServerError_when_deleteEconomicDataEmployedThrowsDBException() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.internalServerError().build();
        doThrow(DBException.class).when(economicDataService).deleteEconomicDataEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnNoContent_when_economicDataSelfEmployedIsDeleted() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        doNothing().when(economicDataService).deleteEconomicDataSelfEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataSelfEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnNotFound_when_deleteEconomicDataSelfEmployedThrowsNotFoundException() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        doThrow(EconomicDataNotFoundException.class).when(economicDataService).deleteEconomicDataSelfEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataSelfEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnInternalServerError_when_deleteEconomicDataSelfEmployedThrowsDBException() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.internalServerError().build();
        doThrow(DBException.class).when(economicDataService).deleteEconomicDataSelfEmployedFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataSelfEmployedFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

}
