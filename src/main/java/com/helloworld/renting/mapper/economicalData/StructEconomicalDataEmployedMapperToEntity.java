package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructEconomicalDataEmployedMapperToEntity {
    EconomicDataEmployed toEntity(EconomicDataEmployedDto dto);
}
