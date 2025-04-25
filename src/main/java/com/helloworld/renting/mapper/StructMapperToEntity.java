package com.helloworld.renting.mapper;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StructMapperToEntity {
    // De DTO → Entity
    Client toEntity(ClientDto dto);

    // Para listas
    List<Client> toEntityList(List<ClientDto> dtoList);
}