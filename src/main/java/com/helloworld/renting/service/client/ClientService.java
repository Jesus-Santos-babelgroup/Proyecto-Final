package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto mapperToDto;
    private final StructMapperToEntity mapperToEntity;

    public ClientService(ClientMapper clientMapper, StructMapperToDto mapperToDto, StructMapperToEntity mapperToEntity) {
        this.clientMapper = clientMapper;
        this.mapperToDto = mapperToDto;
        this.mapperToEntity = mapperToEntity;
    }


    public ClientDto updateClient(ClientDto clientDto) {

        Client client = mapperToEntity.toEntity(clientDto);
        /*
        Long id = client.getId();

        Client existingClient = clientMapper.findById(client.getId());

        if (existingClient != null) {
            int rows = clientMapper.updateClient(client);
            if (rows > 0) {
                ClientDto updatedDto = mapperToDto.toDto(client);
                return Optional.of(updatedDto);
            }
        }
        return Optional.empty(); */

        int rows = clientMapper.updateClient(client);
        if (rows > 0) {
            return mapperToDto.toDto(client);
        }

        return null;


    }
}