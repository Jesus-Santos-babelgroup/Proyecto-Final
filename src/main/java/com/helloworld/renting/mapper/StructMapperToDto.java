package com.helloworld.renting.mapper;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    // De Entity → DTO
    ClientDto toDto(Client entity);

    // Para listas
    List<ClientDto> toDtoList(List<Client> list);
}