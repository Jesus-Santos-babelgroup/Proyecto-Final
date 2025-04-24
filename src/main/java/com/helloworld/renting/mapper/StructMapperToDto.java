package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    // De Entity → DTO
    ClientDto toDto(Client entity);

    // Para listas
    List<ClientDto> toDtoList(List<Client> list);
}