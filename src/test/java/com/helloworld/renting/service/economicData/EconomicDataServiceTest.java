package com.helloworld.renting.service.economicData;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.RentingException;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
import com.helloworld.renting.mapper.economicalData.EconomicDataEmployedMapper;
import com.helloworld.renting.mapper.economicalData.EconomicDataSelfEmployedMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EconomicDataServiceTest {

    EconomicDataService sut;

    @Mock
    EconomicDataEmployedMapper economicDataEmployedMapper;

    @Mock
    EconomicDataSelfEmployedMapper economicDataSelfEmployedMapper;

    @InjectMocks
    private EconomicDataService service;

    @Mock private EconomicDataSelfEmployedMapper selfEmployedMapper;
    @Mock private EconomicDataEmployedMapper employedMapper;
    @Mock private StructMapperToEntity structMapperToEntity;
    @Mock private StructMapperToDto structMapperToDto;
    @Mock private ClientMapper clientMapper;

    @BeforeEach
    void setup() {
        sut = new EconomicDataService(economicDataSelfEmployedMapper, economicDataEmployedMapper, clientMapper, structMapperToDto, structMapperToEntity);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddSelfEmployedSuccessfully() {
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setGrossIncome(BigDecimal.TEN);
        dto.setNetIncome(BigDecimal.ONE);
        dto.setYearEntry(2024);

        EconomicDataSelfEmployed entity = new EconomicDataSelfEmployed();

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(clientMapper.findById(1L)).thenReturn(new com.helloworld.renting.entities.Client());
        when(structMapperToDto.clientToDto(any())).thenReturn(dto.getClient()); // opcional, según cómo sea el DTO
        when(selfEmployedMapper.existsSelfEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(structMapperToEntity.economicalDataSelfEmployedToEntity(dto)).thenReturn(entity);
        when(structMapperToDto.economicalDataSelfEmployedToDto(entity)).thenReturn(dto);

        EconomicDataSelfEmployedDto result = service.addEconomicDataSelfEmployed(dto, 1L);

        assertEquals(dto, result);
        verify(selfEmployedMapper).insert(entity);
    }

    @Test
    void shouldThrowWhenDuplicateSelfEmployedYear() {
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setYearEntry(2024);

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(selfEmployedMapper.existsSelfEmployedByClientIdAndYear(1L, 2024)).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataSelfEmployed(dto, 1L));

        assertEquals(409, ex.getStatusCode().value());
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setYearEntry(2024);

        when(clientMapper.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataSelfEmployed(dto, 1L));

        assertEquals(400, ex.getStatusCode().value());
        assertEquals("El cliente no existe", ex.getReason());
    }

    @Test
    void shouldAddEmployedSuccessfully() {
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        dto.setGrossIncome(BigDecimal.TEN);
        dto.setNetIncome(BigDecimal.ONE);
        dto.setStartDate(LocalDate.of(2024, 1, 1));
        dto.setEndDate(LocalDate.of(2024, 12, 31));
        dto.setYearEntry(2024);

        EconomicDataEmployed entity = new EconomicDataEmployed();

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(clientMapper.findById(1L)).thenReturn(new com.helloworld.renting.entities.Client());
        when(structMapperToDto.clientToDto(any())).thenReturn(dto.getClient());
        when(employedMapper.existsEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(structMapperToEntity.economicalDataEmployedToEntity(dto)).thenReturn(entity);
        when(structMapperToDto.economicalDataEmployedToDto(entity)).thenReturn(dto);

        EconomicDataEmployedDto result = service.addEconomicDataEmployed(dto, 1L);

        assertEquals(dto, result);
        verify(employedMapper).insert(entity);
    }

    @Test
    void shouldThrowWhenStartDateInFuture() {
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now());
        dto.setYearEntry(LocalDate.now().getYear());

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(employedMapper.existsEmployedByClientIdAndYear(anyLong(), anyInt())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataEmployed(dto, 1L));

        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("La fecha no puede estar en el futuro"));
    }

    @Test
    void shouldThrowWhenStartDateYearDoesNotMatch() {
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        dto.setStartDate(LocalDate.of(2023, 1, 1));
        dto.setEndDate(LocalDate.of(2023, 12, 31));
        dto.setYearEntry(2024);

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(employedMapper.existsEmployedByClientIdAndYear(anyLong(), anyInt())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataEmployed(dto, 1L));

        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("El año de entrada debe coincidir"));
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