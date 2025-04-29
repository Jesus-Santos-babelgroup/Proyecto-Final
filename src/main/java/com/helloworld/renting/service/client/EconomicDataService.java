package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.economicalData.EconomicalDataSelfEmployedMapper;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataSelfEmployedMapperToDto;
import com.helloworld.renting.mapper.economicalData.StructEconomicalDataSelfEmployedMapperToEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EconomicDataService {

    private final EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper;
    private final StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto;
    private final StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity;
    private final ClientMapper clientMapper;

    public EconomicDataService(EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper,
                               StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto,
                               StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity,
                               ClientMapper clientMapper){
        this.economicalDataSelfEmployedMapper = economicalDataSelfEmployedMapper;
        this.selfEmployedMapperToDto = selfEmployedMapperToDto;
        this.selfEmployedMapperToEntity = selfEmployedMapperToEntity;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public EconomicDataSelfEmployedDto addEconomicData (EconomicDataSelfEmployedDto economicDataSelfEmployedDto){
        EconomicDataSelfEmployed economicDataSelfEmployed = selfEmployedMapperToEntity.toEntity(economicDataSelfEmployedDto);
        System.out.println(economicDataSelfEmployed);
        economicalDataSelfEmployedMapper.insert(economicDataSelfEmployed);

        return economicDataSelfEmployedDto;
    }

}
