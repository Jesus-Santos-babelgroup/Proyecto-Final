package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructEconomicalDataSelfEmployedMapperToEntity {
    EconomicDataSelfEmployed toEntity(EconomicDataSelfEmployedDto dto);
}
