package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class ClientService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto toDto;
    private final StructMapperToEntity toEntity;
    private final CountryMapper countryMapper;
    private final AddressMapper addressMapper;

    public ClientService(ClientMapper clientMapper,
                         StructMapperToDto toDto,
                         StructMapperToEntity toEntity,
                         CountryMapper countryMapper,
                         AddressMapper addressMapper) {
        this.clientMapper = clientMapper;
        this.toDto = toDto;
        this.toEntity = toEntity;
        this.countryMapper = countryMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public ClientDto createClient(ClientDto clientDto) {
        if (clientDto == null) {
            throw new InvalidClientDtoException("El DTO del cliente no puede ser nulo");
        }

        // Validación de formato de fecha de nacimiento y que no sea futura
        try {
            if (clientDto.getDateOfBirth() != null) {
                LocalDate birthDate = clientDto.getDateOfBirth();
                if (birthDate.isAfter(LocalDate.now())) {
                    throw new InvalidClientDtoException("La fecha de nacimiento no puede ser futura");
                }
            }
        } catch (InvalidClientDtoException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidClientDtoException("Error al procesar la fecha de nacimiento: " + e.getMessage());
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

        // Validación de existencia del país
        if (clientDto.getCountryId() == null) {
            throw new InvalidClientDtoException("El ID del país no puede ser nulo");
        }

        Long countryId;
        try {
            countryId = Long.parseLong(clientDto.getCountryId());
        } catch (NumberFormatException e) {
            throw new InvalidClientDtoException("El ID del país debe ser un número válido");
        }

        if (!countryMapper.existsByCountryId(countryId)) {
            throw new InvalidClientDtoException("El país con ID " + clientDto.getCountryId() + " no existe en la base de datos");
        }

        // Validación de existencia de la dirección
        if (clientDto.getAddressId() == null) {
            throw new InvalidClientDtoException("El ID de la dirección no puede ser nulo");
        }

        if (!addressMapper.existsByAddressId(clientDto.getAddressId())) {
            throw new InvalidClientDtoException("La dirección con ID " + clientDto.getAddressId() + " no existe en la base de datos");
        }

        // Converting to entity
        Client client = toEntity.toEntity(clientDto);

        clientMapper.insert(client);

        // Converting to DTO
        return toDto.toDto(client);
    }}