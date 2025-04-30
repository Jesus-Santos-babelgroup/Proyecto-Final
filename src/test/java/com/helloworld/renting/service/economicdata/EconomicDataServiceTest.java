package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.RentingException;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.EconomicDataEmployedMapper;
import com.helloworld.renting.mapper.EconomicDataSelfEmployedMapper;
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
    EconomicDataEmployedMapper economicDataEmployedMapper;

    @Mock
    EconomicDataSelfEmployedMapper economicDataSelfEmployedMapper;

    @BeforeEach
    void setUp() {
        sut = new EconomicDataService(economicDataEmployedMapper, economicDataSelfEmployedMapper);
    }

    @Test
    void should_delete_when_economicDataEmployedExists() {
        // Given
        Long id = 1L;
        lenient().doNothing().when(economicDataEmployedMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataEmployedMapper).getEconomicDataEmployedByClientId(id);

        // When
        sut.deleteEconomicDataEmployedFromClient(id);

        // Then
        verify(economicDataEmployedMapper).deleteEconomicDataEmployedByClientId(id);
    }

    @Test
    void should_throwException_when_noEconomicDataEmployedExists() {
        // Given
        String message = "No economic data employed found for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataEmployedMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of()).when(economicDataEmployedMapper).getEconomicDataEmployedByClientId(id);

        // When
        EconomicDataNotFoundException exception = assertThrows(EconomicDataNotFoundException.class, () -> sut.deleteEconomicDataEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_delete_when_economicDataSelfEmployedExists() {
        // Given
        Long id = 1L;
        lenient().doNothing().when(economicDataSelfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataSelfEmployedMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        sut.deleteEconomicDataSelfEmployedFromClient(id);

        // Then
        verify(economicDataSelfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
    }

    @Test
    void should_throwException_when_noEconomicDataSelfEmployedExists() {
        // Given
        String message = "No economic data self employed found for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataSelfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of()).when(economicDataSelfEmployedMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        EconomicDataNotFoundException exception = assertThrows(EconomicDataNotFoundException.class, () -> sut.deleteEconomicDataSelfEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_deleteEconomicDataEmployedFails() {
        // Given
        String message = "Error deleting economic data employed from client";
        Long id = 1L;
        lenient().doThrow(RentingException.class).when(economicDataEmployedMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataEmployed())).when(economicDataEmployedMapper).getEconomicDataEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_deleteEconomicDataSelfEmployedFails() {
        // Given
        String message = "Error deleting economic data self employed from client";
        Long id = 1L;
        lenient().doThrow(RentingException.class).when(economicDataSelfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doReturn(List.of(new EconomicDataSelfEmployed())).when(economicDataSelfEmployedMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataSelfEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_getEconomicDataEmployedFails() {
        // Given
        String message = "Error checking if economic data employed exists for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataEmployedMapper).deleteEconomicDataEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataEmployedMapper).getEconomicDataEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void should_throwException_when_getEconomicDataSelfEmployedFails() {
        // Given
        String message = "Error checking if economic data self employed exists for client";
        Long id = 1L;
        lenient().doNothing().when(economicDataSelfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
        lenient().doThrow(DBException.class).when(economicDataSelfEmployedMapper).getEconomicDataSelfEmployedByClientId(id);

        // When
        DBException exception = assertThrows(DBException.class, () -> sut.deleteEconomicDataSelfEmployedFromClient(id));

        // Then
        assertEquals(message, exception.getMessage());
    }

}