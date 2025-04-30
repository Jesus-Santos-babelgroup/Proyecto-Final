package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.RentingException;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.EconomicDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EconomicDataServiceTest {

    EconomicDataService sut;

    @Mock
    EconomicDataMapper economicDataMapper;

    @BeforeEach
    void setUp() {
        sut = new EconomicDataService(economicDataMapper);
    }

    @Test
    void should_delete_when_economicDataExists() {
        // Given
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        sut.deleteEconomicDataFromClient(id);

        // Then
        verify(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        verify(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
    }

    @Test
    void should_throwException_when_noEconomicDataExists() {
        // Given
        String message = "No economic data found for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of()).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of()).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        EconomicDataNotFoundException exception = assertThrows(EconomicDataNotFoundException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_deleteEconomicDataEmployedFails() {
        // Given
        String message = "Error deleting economic data from client";
        Long id = 1L;
        lenient().doThrow(RentingException.class).when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_deleteEconomicDataSelfEmployedFails() {
        // Given
        String message = "Error deleting economic data from client";
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doThrow(RentingException.class).when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_deleteEconomicDataFails() {
        // Given
        String message = "Error deleting economic data from client";
        Long id = 1L;
        lenient().doThrow(RentingException.class).when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doThrow(RentingException.class).when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_getEconomicDataEmployedFails() {
        // Given
        String message = "Error checking if economic data exists for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_getEconomicDataSelfEmployedFails() {
        // Given
        String message = "Error checking if economic data exists for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of()).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_getEconomicDataFails() {
        // Given
        String message = "Error checking if economic data exists for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doNothing().when(economicDataMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataMapper).getEconomicDataEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

}