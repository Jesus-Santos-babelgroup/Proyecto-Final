package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ClientDto updateClient(ClientDto clientDto) {

        //comprobar el acceso a la edición

        if (!clientMapper.existById(clientDto.getId())) {
            throw new InvalidClientDtoException("El ID no existe");
        }

        //comprobar cosas nulas

        if (clientDto.getName() == null) {
            throw new AttributeException("El nombre no puede ser NULL");
        }
        if (clientDto.getFirstSurname() == null) {
            throw new AttributeException("Los apellidos no pueden ser NULL");
        }
        if (clientDto.getPhone() == null) {
            throw new AttributeException("El tlf no puede ser NULL");
        }
        if (clientDto.getEmail() == null) {
            throw new AttributeException("El email no puede ser NULL");
        }
        if (clientDto.getNif() == null) {
            throw new AttributeException("El NIF no puede ser NULL");
        }
        if (clientDto.getDateOfBirth() == null) {
            throw new AttributeException("La fecha de nacimiento no puede ser NULL");
        }
        if (clientDto.getAddressId() == null) {
            throw new AttributeException("La dirección no puede ser NULL");
        }

        if (clientDto.getCountryId() == null) {
            throw new AttributeException("El país no puede ser NULL");
        }

        //comprobar duplicados

        if (clientMapper.existByNif(clientDto.getNif())) {
            throw new DuplicateModel("Este NIF ya existe");
        }

        if (clientMapper.existByEmail(clientDto.getEmail())) {
            throw new DuplicateModel("Este email ya existe");
        }

        if (clientMapper.existByTlf(clientDto.getPhone())) {
            throw new DuplicateModel("Este tlf ya existe");
        }

        Client client = mapperToEntity.toEntity(clientDto);
        clientMapper.updateClient(client);
        return mapperToDto.toDto(client);
    }
}