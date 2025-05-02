package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.entities.Address;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToEntity {
    Client clientToEntity(ClientDto dto);

    Country countryToEntity(CountryDto dto);

    Address addressToEntity(Address dto);
}