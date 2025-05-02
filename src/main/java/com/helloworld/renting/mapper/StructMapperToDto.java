package com.helloworld.renting.mapper;
import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructMapperToDto {
    ClientDto clientToDto(Client entity);
}