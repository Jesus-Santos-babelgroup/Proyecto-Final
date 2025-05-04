package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.*;
import com.helloworld.renting.entities.Address;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.Country;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToEntity {
    Client clientToEntity(ClientDto dto);

    Country countryToEntity(CountryDto dto);

    Address addressToEntity(AddressDto dto);

    EconomicDataEmployed economicalDataEmployedToEntity(EconomicDataEmployedDto dto);

    EconomicDataSelfEmployed economicalDataSelfEmployedToEntity(EconomicDataSelfEmployedDto dto);
}