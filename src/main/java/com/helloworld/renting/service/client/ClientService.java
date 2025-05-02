package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;


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
        validateName(clientDto.getName());
        validateFirstSurname(clientDto.getFirstSurname());
        validateSecondSurname(clientDto.getSecondSurname());
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

    private void validateClientNotNull(ClientDto clientDto) {
        if (clientDto == null) {
            throw new InvalidClientDtoException("El DTO del cliente no puede ser nulo");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth) {
        try {
            if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now())) {
                throw new InvalidClientDtoException("La fecha de nacimiento no puede ser futura");
            }
        } catch (InvalidClientDtoException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidClientDtoException("Error al procesar la fecha de nacimiento: " + e.getMessage());
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
            throw new InvalidClientDtoException("El país con ID " + countryId + " no existe en la base de datos");
        }
    }

    private void validateAddress(Long addressId) {
        if (addressId == null) {
            throw new InvalidClientDtoException("El ID de la dirección no puede ser nulo");
        }

        if (!addressMapper.existsByAddressId(addressId)) {
            throw new InvalidClientDtoException("La dirección con ID " + addressId + " no existe en la base de datos");
        }
    }
    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidClientDtoException("El nombre no puede ser nulo o vacío");
        }

        String namePattern = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' -]{1,60}$";
        if (!name.matches(namePattern)) {
            throw new InvalidClientDtoException("El nombre debe contener solo letras, espacios, apóstrofes o guiones y tener máximo 60 caracteres");
        }
    }

    private void validateFirstSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            throw new InvalidClientDtoException("El primer apellido no puede ser nulo o vacío");
        }

        String namePattern = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' -]{1,60}$";
        if (!surname.matches(namePattern)) {
            throw new InvalidClientDtoException("El primer apellido debe contener solo letras, espacios, apóstrofes o guiones y tener máximo 60 caracteres");
        }
    }

    private void validateSecondSurname(String surname) {
        // El segundo apellido puede ser nulo o vacío, solo validamos si tiene contenido
        if (surname != null && !surname.isEmpty()) {
            String namePattern = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' -]{1,60}$";
            if (!surname.matches(namePattern)) {
                throw new InvalidClientDtoException("El segundo apellido debe contener solo letras, espacios, apóstrofes o guiones y tener máximo 60 caracteres");
            }
        }
    }

    @Transactional
    public void deleteClientById(Long id) {

        if (!clientMapper.existsById(id)) {
            throw new ClientNotFoundException("Cliente no encontrado con ID " + id);
        }


        boolean deleted = clientMapper.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("No se pudo eliminar el cliente con ID " + id);
        }
    }

}