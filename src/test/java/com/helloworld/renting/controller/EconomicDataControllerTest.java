package com.helloworld.renting.controller;

import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.service.economicdata.EconomicDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class EconomicDataControllerTest {

    private EconomicDataController sut;

    @Mock
    private EconomicDataService economicDataService;

    @BeforeEach
    void setUp() {
        sut = new EconomicDataController(economicDataService);
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