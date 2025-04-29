package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToEntity;
import com.helloworld.renting.mapper.StructMapperToDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
public class ClientService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto toDto;
    private final StructMapperToEntity toEntity;

    public ClientService(ClientMapper clientMapper,
                         StructMapperToDto toDto,
                         StructMapperToEntity toEntity) {
        this.clientMapper = clientMapper;
        this.toDto = toDto;
        this.toEntity = toEntity;
    }

    @Transactional
    public ClientDto createClient(ClientDto clientDto) {
        if (clientDto == null) {
            throw new InvalidClientDtoException("El DTO del cliente no puede ser nulo");
        }

// Validación de formato de fecha de nacimiento y que no sea futura
        try {
            if (clientDto.getDateOfBirth() != null) {
                // No necesitas parsear si ya es un LocalDate
                LocalDate birthDate = clientDto.getDateOfBirth();
                if (birthDate.isAfter(LocalDate.now())) {
                    throw new InvalidClientDtoException("La fecha de nacimiento no puede ser futura");
                }
            }
        } catch (InvalidClientDtoException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidClientDtoException("Error al procesar la fecha de nacimiento");
        }

        if (clientMapper.existsByNif(clientDto.getNif())) {
            throw new DuplicateModel("Ya existe un cliente con este NIF: " + clientDto.getNif());
        }

        if (clientMapper.existsByEmail(clientDto.getEmail())) {
            throw new DuplicateModel("Ya existe un cliente con este email: " + clientDto.getEmail());
        }

        if (clientDto.getScoring() == null || clientDto.getScoring() < 0) {
            throw new InvalidClientDtoException("El scoring no puede ser nulo ni negativo");
        }

        // Converting to entity
        Client client = toEntity.toEntity(clientDto);

        clientMapper.insert(client);

        // Converting to DTO
        return toDto.toDto(client);
    }


    @Transactional
    public ClientDto updateClient(ClientDto clientDto) {

        //comprobar el acceso a la edición

        if (!clientMapper.existsById(clientDto.getId())) {
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

        if (clientMapper.existsByNif(clientDto.getNif())) {
            throw new DuplicateModel("Este NIF ya existe");
        }

        if (clientMapper.existsByEmail(clientDto.getEmail())) {
            throw new DuplicateModel("Este email ya existe");
        }

        if (clientMapper.existsByTlf(clientDto.getPhone())) {
            throw new DuplicateModel("Este tlf ya existe");
        }

        Client client = toEntity.toEntity(clientDto);
        clientMapper.updateClient(client);
        return toDto.toDto(client);
    }
}