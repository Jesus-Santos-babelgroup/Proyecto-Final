package com.helloworld.renting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.service.economicData.EconomicDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class EconomicDataControllerTest {

    private EconomicDataController sut;

    @Mock
    private EconomicDataService economicDataService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDto clientDto;
    private EconomicDataSelfEmployedDto selfEmployedDto;
    private EconomicDataEmployedDto employedDto;

    @BeforeEach
    void setup() {
        sut = new EconomicDataController(economicDataService);
        selfEmployedDto = new EconomicDataSelfEmployedDto();
        clientDto = new ClientDto();
        clientDto.setId(1L);
        selfEmployedDto.setClient(clientDto);
        selfEmployedDto.setGrossIncome(BigDecimal.valueOf(50000));
        selfEmployedDto.setNetIncome(BigDecimal.valueOf(30000));
        selfEmployedDto.setYearEntry(2023);

        employedDto = new EconomicDataEmployedDto();
        employedDto.setClient(clientDto);
        employedDto.setCif("B12345678");
        employedDto.setGrossIncome(BigDecimal.valueOf(40000));
        employedDto.setNetIncome(BigDecimal.valueOf(25000));
        employedDto.setStartDate(LocalDate.of(2023, 1, 1));
        employedDto.setEndDate(LocalDate.of(2023, 12, 31));
        employedDto.setYearEntry(2023);
    }

    /*
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
    */

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