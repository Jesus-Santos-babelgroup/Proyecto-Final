package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.economicalData.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EconomicDataServiceTest {

    @InjectMocks
    private EconomicDataService service;

    @Mock private EconomicalDataSelfEmployedMapper selfEmployedMapper;
    @Mock private EconomicalDataEmployedMapper employedMapper;
    @Mock private StructEconomicalDataSelfEmployedMapperToDto selfEmployedDtoMapper;
    @Mock private StructEconomicalDataSelfEmployedMapperToEntity selfEmployedEntityMapper;
    @Mock private StructEconomicalDataEmployedMapperToDto employedDtoMapper;
    @Mock private StructEconomicalDataEmployedMapperToEntity employedEntityMapper;
    @Mock private ClientMapper clientMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddSelfEmployedSuccessfully() {
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setGrossIncome(BigDecimal.TEN);
        dto.setNetIncome(BigDecimal.ONE);
        dto.setYearEntry(2024);

        EconomicDataSelfEmployed entity = new EconomicDataSelfEmployed();
        when(selfEmployedEntityMapper.toEntity(dto)).thenReturn(entity);
        when(selfEmployedMapper.existsSelfEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(selfEmployedDtoMapper.toDto(entity)).thenReturn(dto);

        EconomicDataSelfEmployedDto result = service.addEconomicDataSelfEmployed(dto, 1L);

        assertEquals(dto, result);
        verify(selfEmployedMapper).insert(entity);
    }

    @Test
    void shouldThrowWhenDuplicateSelfEmployedYear() {
        when(selfEmployedMapper.existsSelfEmployedByClientIdAndYear(1L, 2024)).thenReturn(true);
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setYearEntry(2024);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataSelfEmployed(dto, 1L));

        assertEquals(409, ex.getStatusCode().value());
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
        when(employedEntityMapper.toEntity(dto)).thenReturn(entity);
        when(employedMapper.existsEmployedByClientIdAndYear(1L, 2024)).thenReturn(false);
        when(employedDtoMapper.toDto(entity)).thenReturn(dto);

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

        when(employedMapper.existsEmployedByClientIdAndYear(anyLong(), anyInt())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.addEconomicDataEmployed(dto, 1L));

        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("El año de entrada debe coincidir"));
    }

}
