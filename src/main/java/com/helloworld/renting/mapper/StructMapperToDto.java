package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.AddressDto;
import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.entities.Address;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    ClientDto clientToDto(Client entity);

    CountryDto countryToDto(Country entity);

    AddressDto addressToDto(Address entity);
}