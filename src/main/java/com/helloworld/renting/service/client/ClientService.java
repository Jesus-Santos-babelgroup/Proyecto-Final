package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;

public class ClientService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto mapperToDto;
    private final StructMapperToEntity mapperToEntity;

    public ClientService(ClientMapper clientMapper, StructMapperToDto mapperToDto, StructMapperToEntity mapperToEntity) {
        this.clientMapper = clientMapper;
        this.mapperToDto = mapperToDto;
        this.mapperToEntity = mapperToEntity;
    }


    public Client updateClient(ClientDto clientDto) {

        Client client = mapperToEntity.toEntity(clientDto);

        return clientMapper.updateClient(client);
    }
}