package com.helloworld.renting.mapper;

import org.mapstruct.Mapper;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.dto.ClientDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StructMapperToEntity {
    // De DTO → Entity
    Client toEntity(ClientDto dto);

    // Para listas
    List<Client> toEntityList(List<ClientDto> dtoList);
}