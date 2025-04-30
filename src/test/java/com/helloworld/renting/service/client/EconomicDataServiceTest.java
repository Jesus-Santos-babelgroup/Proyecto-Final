package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.mapper.economicalData.EconomicalDataEmployedMapper;
import com.helloworld.renting.mapper.economicalData.EconomicalDataSelfEmployedMapper;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataEmployedMapperToDto;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataEmployedMapperToEntity;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataSelfEmployedMapperToDto;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataSelfEmployedMapperToEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EconomicDataServiceTest {

    private EconomicalDataSelfEmployedMapper selfEmployedMapper;
    private EconomicalDataEmployedMapper employedMapper;
    private StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto;
    private StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity;
    private StructEconomicalDataEmployedMapperToDto employedMapperToDto;
    private StructEconomicalDataEmployedMapperToEntity employedMapperToEntity;

    private EconomicDataService economicDataService;

    @BeforeEach
    void setUp() {
        selfEmployedMapper = mock(EconomicalDataSelfEmployedMapper.class);
        employedMapper = mock(EconomicalDataEmployedMapper.class);
        selfEmployedMapperToDto = mock(StructEconomicalDataSelfEmployedMapperToDto.class);
        selfEmployedMapperToEntity = mock(StructEconomicalDataSelfEmployedMapperToEntity.class);
        employedMapperToDto = mock(StructEconomicalDataEmployedMapperToDto.class);
        employedMapperToEntity = mock(StructEconomicalDataEmployedMapperToEntity.class);

        economicDataService = new EconomicDataService(
                selfEmployedMapper,
                employedMapper,
                selfEmployedMapperToDto,
                selfEmployedMapperToEntity,
                employedMapperToDto,
                employedMapperToEntity
        );
    }

    @Test
    void editSelfEmployedData_success() {
        Long id = 1L;
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();
        dto.setGrossIncome(BigDecimal.valueOf(5000));
        dto.setNetIncome(BigDecimal.valueOf(3000));
        dto.setYearEntry(2020);

        EconomicDataSelfEmployed existingEntity = new EconomicDataSelfEmployed();
        EconomicDataSelfEmployed updatedEntity = new EconomicDataSelfEmployed();

        when(selfEmployedMapper.findById(id)).thenReturn(existingEntity);
        when(selfEmployedMapperToEntity.toEntity(dto)).thenReturn(updatedEntity);
        when(selfEmployedMapper.update(updatedEntity)).thenReturn(1);
        when(selfEmployedMapperToDto.toDto(updatedEntity)).thenReturn(dto);

        EconomicDataSelfEmployedDto result = economicDataService.editSelfEmployedData(id, dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(selfEmployedMapper).findById(id);
        verify(selfEmployedMapper).update(updatedEntity);
    }

    @Test
    void editSelfEmployedData_notFound() {
        Long id = 1L;
        EconomicDataSelfEmployedDto dto = new EconomicDataSelfEmployedDto();

        when(selfEmployedMapper.findById(id)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                economicDataService.editSelfEmployedData(id, dto));

        assertEquals("Datos autónomo no encontrados para ID: 1", exception.getMessage());
    }

    @Test
    void editEmployedData_success() {
        Long id = 1L;
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();
        dto.setCif("A12345678");
        dto.setGrossIncome(BigDecimal.valueOf(4000));
        dto.setNetIncome(BigDecimal.valueOf(2500));
        dto.setYearEntry(2019);

        EconomicDataEmployed existingEntity = new EconomicDataEmployed();
        EconomicDataEmployed updatedEntity = new EconomicDataEmployed();

        when(employedMapper.findById(id)).thenReturn(existingEntity);
        when(employedMapperToEntity.toEntity(dto)).thenReturn(updatedEntity);
        when(employedMapper.update(updatedEntity)).thenReturn(1);
        when(employedMapperToDto.toDto(updatedEntity)).thenReturn(dto);

        EconomicDataEmployedDto result = economicDataService.editEmployedData(id, dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(employedMapper).findById(id);
        verify(employedMapper).update(updatedEntity);
    }

    @Test
    void editEmployedData_notFound() {
        Long id = 1L;
        EconomicDataEmployedDto dto = new EconomicDataEmployedDto();

        when(employedMapper.findById(id)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                economicDataService.editEmployedData(id, dto));

        assertEquals("Datos empleado no encontrados para ID: 1", exception.getMessage());
    }
}
