package com.helloworld.renting.service.economicData;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EconomicDataServiceTest {

    @InjectMocks
    private EconomicDataService service;

    @Mock
    private EconomicDataEmployedMapper employedMapper;
    @Mock
    private EconomicDataSelfEmployedMapper selfEmployedMapper;
    @Mock
    private StructMapperToEntity structMapperToEntity;
    @Mock
    private StructMapperToDto structMapperToDto;
    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    void setup() {
        // Mockito annotations ya manejado por @ExtendWith y @InjectMocks
    }


    @Test
    void shouldAddSelfEmployedSuccessfully() {
        ClientDto clientDto = new ClientDto();
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        clientDto.setId(1L);
        dto.setClient(clientDto);
        dto.setGrossIncome(BigDecimal.TEN);
        dto.setNetIncome(BigDecimal.ONE);
        dto.setYearEntry(2024);

        EconomicDataSelfEmployed entity = new EconomicDataSelfEmployed();

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(selfEmployedMapper.existsSelfEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(structMapperToEntity.economicalDataSelfEmployedToEntity(dto)).thenReturn(entity);
        when(structMapperToDto.economicalDataSelfEmployedToDto(entity)).thenReturn(dto);

        EconomicDataSelfEmployedDto result = service.addEconomicDataSelfEmployed(dto, 1L);

        assertEquals(dto, result);
        verify(selfEmployedMapper).insert(entity);
    }

    @Test
    void shouldThrowWhenDuplicateSelfEmployedYear() {
        ClientDto clientDto = new ClientDto();
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        clientDto.setId(1L);
        dto.setClient(clientDto);
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
        ClientDto clientDto = new ClientDto();
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        clientDto.setId(1L);
        dto.setClient(clientDto);
        dto.setGrossIncome(BigDecimal.TEN);
        dto.setStartDate(LocalDate.of(2024, 1, 1));
        dto.setEndDate(LocalDate.of(2024, 12, 31));
        dto.setYearEntry(2024);

        EconomicDataEmployed entity = new EconomicDataEmployed();

        when(clientMapper.existsById(1L)).thenReturn(true);
        when(employedMapper.existsEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(structMapperToEntity.economicalDataEmployedToEntity(dto)).thenReturn(entity);
        when(structMapperToDto.economicalDataEmployedToDto(entity)).thenReturn(dto);

        EconomicDataEmployedDto result = service.addEconomicDataEmployed(dto, 1L);

        assertEquals(dto, result);
        verify(employedMapper).insert(entity);
    }

    @Test
    void shouldThrowWhenStartDateInFuture() {
        ClientDto clientDto = new ClientDto();
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        clientDto.setId(1L);
        dto.setClient(clientDto);
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
        ClientDto clientDto = new ClientDto();
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        clientDto.setId(1L);
        dto.setClient(clientDto);
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
    void shouldDeleteWhenEconomicDataEmployedExists() {
        Long id = 1L;
        when(employedMapper.getEconomicDataEmployedByClientId(id)).thenReturn(List.of(new EconomicDataEmployed()));

        service.deleteEconomicDataEmployedFromClient(id);

        verify(employedMapper).deleteEconomicDataEmployedByClientId(id);
    }

    @Test
    void shouldThrowExceptionWhenNoEconomicDataEmployedExists() {
        Long id = 1L;
        when(employedMapper.getEconomicDataEmployedByClientId(id)).thenReturn(List.of());

        EconomicDataNotFoundException ex = assertThrows(EconomicDataNotFoundException.class, () ->
                service.deleteEconomicDataEmployedFromClient(id));

        assertEquals("No economic data employed found for client", ex.getMessage());
    }

    @Test
    void shouldDeleteWhenEconomicDataSelfEmployedExists() {
        Long id = 1L;
        when(selfEmployedMapper.getEconomicDataSelfEmployedByClientId(id)).thenReturn(List.of(new EconomicDataSelfEmployed()));

        service.deleteEconomicDataSelfEmployedFromClient(id);

        verify(selfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);
    }

    @Test
    void shouldThrowExceptionWhenNoEconomicDataSelfEmployedExists() {
        Long id = 1L;
        when(selfEmployedMapper.getEconomicDataSelfEmployedByClientId(id)).thenReturn(List.of());

        EconomicDataNotFoundException ex = assertThrows(EconomicDataNotFoundException.class, () ->
                service.deleteEconomicDataSelfEmployedFromClient(id));

        assertEquals("No economic data self employed found for client", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDeleteEconomicDataEmployedFails() {
        Long id = 1L;
        when(employedMapper.getEconomicDataEmployedByClientId(id)).thenReturn(List.of(new EconomicDataEmployed()));
        doThrow(new RuntimeException()).when(employedMapper).deleteEconomicDataEmployedByClientId(id);

        DBException ex = assertThrows(DBException.class, () ->
                service.deleteEconomicDataEmployedFromClient(id));

        assertEquals("Error deleting economic data employed from client", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDeleteEconomicDataSelfEmployedFails() {
        Long id = 1L;
        when(selfEmployedMapper.getEconomicDataSelfEmployedByClientId(id)).thenReturn(List.of(new EconomicDataSelfEmployed()));
        doThrow(new RuntimeException()).when(selfEmployedMapper).deleteEconomicDataSelfEmployedByClientId(id);

        DBException ex = assertThrows(DBException.class, () ->
                service.deleteEconomicDataSelfEmployedFromClient(id));

        assertEquals("Error deleting economic data self employed from client", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenGetEconomicDataEmployedFails() {
        Long id = 1L;
        when(employedMapper.getEconomicDataEmployedByClientId(id)).thenThrow(new RuntimeException());

        DBException ex = assertThrows(DBException.class, () ->
                service.deleteEconomicDataEmployedFromClient(id));

        assertEquals("Error checking if economic data employed exists for client", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenGetEconomicDataSelfEmployedFails() {
        Long id = 1L;
        when(selfEmployedMapper.getEconomicDataSelfEmployedByClientId(id)).thenThrow(new RuntimeException());

        DBException ex = assertThrows(DBException.class, () ->
                service.deleteEconomicDataSelfEmployedFromClient(id));

        assertEquals("Error checking if economic data self employed exists for client", ex.getMessage());
    }

}