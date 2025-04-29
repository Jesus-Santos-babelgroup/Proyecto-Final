package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.economicalData.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EconomicDataService {

    private final EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper;
    private final EconomicalDataEmployedMapper economicalDataEmployedMapper;
    private final StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto;
    private final StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity;
    private final StructEconomicalDataEmployedMapperToDto employedMapperToDto;
    private final StructEconomicalDataEmployedMapperToEntity employedMapperToEntity;
    private final ClientMapper clientMapper;

    public EconomicDataService(EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper,
                               EconomicalDataEmployedMapper economicalDataEmployedMapper,
                               StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto,
                               StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity,
                               StructEconomicalDataEmployedMapperToDto employedMapperToDto,
                               StructEconomicalDataEmployedMapperToEntity employedMapperToEntity,
                               ClientMapper clientMapper){
        this.economicalDataSelfEmployedMapper = economicalDataSelfEmployedMapper;
        this.economicalDataEmployedMapper = economicalDataEmployedMapper;
        this.selfEmployedMapperToDto = selfEmployedMapperToDto;
        this.selfEmployedMapperToEntity = selfEmployedMapperToEntity;
        this.employedMapperToDto = employedMapperToDto;
        this.employedMapperToEntity = employedMapperToEntity;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public EconomicDataSelfEmployedDto addEconomicDataSelfEmployed(
            EconomicDataSelfEmployedDto economicDataSelfEmployedDto,
            Long clientId){
        economicDataSelfEmployedDto.setClientId(clientId);
        EconomicDataSelfEmployed economicDataSelfEmployed = selfEmployedMapperToEntity.toEntity(economicDataSelfEmployedDto);
        economicalDataSelfEmployedMapper.insert(economicDataSelfEmployed);

        return economicDataSelfEmployedDto;
    }


    @Transactional
    public EconomicDataEmployedDto addEconomicDataEmployed(
            EconomicDataEmployedDto economicDataEmployedDto,
            Long clientId){
        economicDataEmployedDto.setClientId(clientId);
        EconomicDataEmployed economicDataEmployed = employedMapperToEntity.toEntity(economicDataEmployedDto);
        System.out.println(economicDataEmployed);
        economicalDataEmployedMapper.insert(economicDataEmployed);
        return economicDataEmployedDto;
    }


    //TODO: Validar datos: no se puede tener mas de 2 datos bancarios del mismo tipo en el mismo año, valores negativos

}
