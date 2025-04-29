package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.attributes.InvalidEmployedDataDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidSelfEmployedDataDtoException;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.mapper.economicalData.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class EconomicDataService {

    private final EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper;
    private final EconomicalDataEmployedMapper economicalDataEmployedMapper;
    private final StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto;
    private final StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity;
    private final StructEconomicalDataEmployedMapperToDto employedMapperToDto;
    private final StructEconomicalDataEmployedMapperToEntity employedMapperToEntity;

    public EconomicDataService(EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper,
                               EconomicalDataEmployedMapper economicalDataEmployedMapper,
                               StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto,
                               StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity,
                               StructEconomicalDataEmployedMapperToDto employedMapperToDto,
                               StructEconomicalDataEmployedMapperToEntity employedMapperToEntity){
        this.economicalDataSelfEmployedMapper = economicalDataSelfEmployedMapper;
        this.economicalDataEmployedMapper = economicalDataEmployedMapper;
        this.selfEmployedMapperToDto = selfEmployedMapperToDto;
        this.selfEmployedMapperToEntity = selfEmployedMapperToEntity;
        this.employedMapperToDto = employedMapperToDto;
        this.employedMapperToEntity = employedMapperToEntity;
    }

    @Transactional
    public EconomicDataSelfEmployedDto addEconomicData(EconomicDataSelfEmployedDto economicDataSelfEmployedDto){
        EconomicDataSelfEmployed economicDataSelfEmployed = selfEmployedMapperToEntity.toEntity(economicDataSelfEmployedDto);
        System.out.println(economicDataSelfEmployed);
        economicalDataSelfEmployedMapper.insert(economicDataSelfEmployed);

        return economicDataSelfEmployedDto;
    }

    @Transactional
    public EconomicDataSelfEmployedDto editSelfEmployedData(Long id, EconomicDataSelfEmployedDto economicDataSelfEmployedDto){
        EconomicDataSelfEmployed existing = economicalDataSelfEmployedMapper.findById(id);
        if (economicDataSelfEmployedDto == null) {
            throw new InvalidEmployedDataDtoException("El DTO no puede ser nulo.");
        }

        if (economicDataSelfEmployedDto.getGrossIncome() == null || economicDataSelfEmployedDto.getGrossIncome().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidEmployedDataDtoException("El gross income debe ser mayor que 0.");
        }

        if (economicDataSelfEmployedDto.getNetIncome() == null || economicDataSelfEmployedDto.getNetIncome().compareTo(economicDataSelfEmployedDto.getGrossIncome()) > 0) {
            throw new InvalidEmployedDataDtoException("El net income no puede ser mayor que el gross income.");
        }

        if (existing == null) {
            throw new NotFoundException("Datos económicos de empleado no encontrados para ID: " + id);
        }
        EconomicDataSelfEmployed entity = selfEmployedMapperToEntity.toEntity(economicDataSelfEmployedDto);
        entity.setId(id);
        economicalDataSelfEmployedMapper.update(entity);

        return selfEmployedMapperToDto.toDto(entity);
    }

    @Transactional
    public EconomicDataEmployedDto editEmployedData(Long id, EconomicDataEmployedDto economicDataEmployedDto) {
        EconomicDataEmployed existing = economicalDataEmployedMapper.findById(id);
        if (existing == null) {
            throw new NotFoundException("Datos económicos de empleado no encontrados para ID: " + id);
        }
        if (economicDataEmployedDto == null) {
            throw new InvalidEmployedDataDtoException("El DTO no puede ser nulo.");
        }

        if (economicDataEmployedDto.getGrossIncome() == null || economicDataEmployedDto.getGrossIncome().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidEmployedDataDtoException("El gross income debe ser mayor que 0.");
        }

        if (economicDataEmployedDto.getNetIncome() == null || economicDataEmployedDto.getNetIncome().compareTo(economicDataEmployedDto.getGrossIncome()) > 0) {
            throw new InvalidEmployedDataDtoException("El net income no puede ser mayor que el gross income.");
        }

        if (economicDataEmployedDto.getStartDate() != null && economicDataEmployedDto.getEndDate() != null &&
                economicDataEmployedDto.getStartDate().isAfter(economicDataEmployedDto.getEndDate())) {
            throw new InvalidEmployedDataDtoException("La fecha de inicio debe ser anterior a la de fin.");
        }

        EconomicDataEmployed entity = employedMapperToEntity.toEntity(economicDataEmployedDto);
        entity.setId(id);
        economicalDataEmployedMapper.update(entity);

        return employedMapperToDto.toDto(entity);
    }



}
