package com.helloworld.renting.mapper;
import com.helloworld.renting.dto.*;
import com.helloworld.renting.entities.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    ClientDto clientToDto(Client entity);

    CountryDto countryToDto(Country entity);

    AddressDto addressToDto(Address entity);

    EconomicDataEmployedDto economicalDataEmployedToDto(EconomicDataEmployed entity);

    EconomicDataSelfEmployedDto economicalDataSelfEmployedToDto(EconomicDataSelfEmployed entity);
}