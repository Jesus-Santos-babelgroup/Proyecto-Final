package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DBException;
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
        validateClientNotNull(clientDto);
        validateDateOfBirth(clientDto.getDateOfBirth());
        checkForDuplicates(clientDto);
        validateScoring(clientDto.getScoring());
        validateCountry(clientDto.getCountryId());
        validateAddress(clientDto.getAddressId());

        // Converting to entity
        Client client = toEntity.toEntity(clientDto);
        clientMapper.insert(client);

        // Converting to DTO
        return toDto.toDto(client);
    }


    @Transactional
    public ClientDto updateClient(ClientDto clientDto) {

        validateIDNotNull(clientDto.getId());
        validateName(clientDto.getName());
        validateFirstSurname(clientDto.getFirstSurname());
        validatePhone(clientDto.getPhone());
        validateEmail(clientDto.getEmail());
        validateNif(clientDto.getNif());
        validateDateOfBirth(clientDto.getDateOfBirth());
        validateCountry(clientDto.getCountryId());
        validateAddress(clientDto.getAddressId());
        checkForDuplicates(clientDto);

        Client client = toEntity.toEntity(clientDto);
        clientMapper.updateClient(client);
        return toDto.toDto(client);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new AttributeException("El nombre no puede ser NULL");
        }
    }

    private void validateFirstSurname(String surname) {
        if (surname == null) {
            throw new AttributeException("Los apellidos no pueden ser NULL");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null) {
            throw new AttributeException("El tlf no puede ser NULL");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new AttributeException("El email no puede ser NULL");
        }
    }

    private void validateIDNotNull(Long id) {
        if (!clientMapper.existsById(id)) {
            throw new InvalidClientDtoException("El ID no existe");
        }
    }

    private void validateNif(String nif) {
        if (nif == null) {
            throw new AttributeException("El NIF no puede ser NULL");
        }
    }

    private void validateClientNotNull(ClientDto clientDto) {
        if (clientDto == null) {
            throw new InvalidClientDtoException("El DTO del cliente no puede ser nulo");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidClientDtoException("La fecha de nacimiento no puede ser futura");
        }

        if (dateOfBirth == null) {
            throw new AttributeException("La fecha de nacimiento no puede ser NULL");

        }
    }

    private void checkForDuplicates(ClientDto clientDto) {
        if (clientMapper.existsByNif(clientDto.getNif())) {
            throw new DuplicateModel("Ya existe un cliente con este NIF: " + clientDto.getNif());
        }

        if (clientMapper.existsByEmail(clientDto.getEmail())) {
            throw new DuplicateModel("Ya existe un cliente con este email: " + clientDto.getEmail());
        }
        if (clientDto.getPhone() != null && clientMapper.existsByPhone(clientDto.getPhone())) {
            throw new DuplicateModel("Ya existe un cliente con este teléfono: " + clientDto.getPhone());
        }
    }

    private void validateScoring(Integer scoring) {
        if (scoring == null || scoring < 0) {
            throw new InvalidClientDtoException("El scoring no puede ser nulo ni negativo");
        }
    }

    private void validateCountry(String countryId) {
        if (countryId == null) {
            throw new InvalidClientDtoException("El ID del país no puede ser nulo");
        }

        Long countryIdLong;
        try {
            countryIdLong = Long.parseLong(countryId);
        } catch (NumberFormatException e) {
            throw new InvalidClientDtoException("El ID del país debe ser un número válido");
        }

        if (!countryMapper.existsByCountryId(countryIdLong)) {
            throw new DBException("El país con ID " + countryId + " no existe en la base de datos");
        }
    }

    private void validateAddress(Long addressId) {
        if (addressId == null) {
            throw new InvalidClientDtoException("El ID de la dirección no puede ser nulo");
        }

        if (!addressMapper.existsByAddressId(addressId)) {
            throw new DBException("La dirección con ID " + addressId + " no existe en la base de datos");
        }
    }

}