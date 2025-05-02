package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToEntity {
    Client clientToEntity(ClientDto dto);

    EconomicDataEmployed economicalDataEmployedToEntity(EconomicDataEmployedDto dto);

    EconomicDataSelfEmployed economicalDataSelfEmployedToEntity(EconomicDataSelfEmployedDto dto);
}