package com.helloworld.renting.mapper;

import org.mapstruct.Mapper;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.dto.ClientDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    // De Entity → DTO
    ClientDto toDto(Client entity);

    // Para listas
    List<ClientDto> toDtoList(List<Client> entityList);
}