package com.helloworld.renting.controller;

import com.helloworld.renting.exceptions.RentingException;
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
    void should_returnNoContent_when_economicDataIsDeleted() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        doNothing().when(economicDataService).deleteEconomicDataFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_returnNotFound_when_serviceThrowsException() {
        // Given
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        doThrow(RentingException.class).when(economicDataService).deleteEconomicDataFromClient(id);

        // When
        ResponseEntity<Void> response = sut.deleteEconomicDataFromClient(id);

        // Then
        assertEquals(expectedResponse, response);
    }
}