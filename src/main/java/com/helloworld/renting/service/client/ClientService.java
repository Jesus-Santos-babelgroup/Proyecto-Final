package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.AddressDto;
import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.entities.Address;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.Country;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;
import com.helloworld.renting.mapper.*;
import com.helloworld.renting.service.address.AddressService;
import com.helloworld.renting.service.country.CountryService;
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
    private final AddressService addressService;
    private final CountryService countryService;


    public ClientService(ClientMapper clientMapper,
                         StructMapperToDto toDto,
                         StructMapperToEntity toEntity,
                         CountryMapper countryMapper,
                         AddressMapper addressMapper, AddressService addressService, CountryService countryService) {
        this.clientMapper = clientMapper;
        this.toDto = toDto;
        this.toEntity = toEntity;
        this.countryMapper = countryMapper;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
        this.countryService = countryService;
    }

    @Transactional
    public ClientDto createClient(ClientDto clientDto) {
        validateClientNotNull(clientDto);
        validateName(clientDto.getName());
        validateFirstSurname(clientDto.getFirstSurname());
        validatePhone(clientDto.getPhone());
        validateEmail(clientDto.getEmail());
        validateNif(clientDto.getNif());
        validateDateOfBirth(clientDto.getDateOfBirth());
        checkForDuplicates(clientDto);
        validateScoring(clientDto.getScoring());
        validateCountry(clientDto.getCountry());
        validateAddress(clientDto.getAddress());
        validateAddress(clientDto.getNotificationAddress());
        AddressDto notiAddressDto = validateEqualsAddresses(clientDto.getAddress(), clientDto.getNotificationAddress());

        Client client = toEntity.clientToEntity(clientDto);
        String idCountry = countryService.getIdCountry(clientDto.getCountry());
        client.setCountryId(idCountry);
        Long idAddress = addressService.getIdAddress(clientDto.getAddress());
        client.setAddressId(idAddress);
        Long idNotiAddress = addressService.getIdAddress(clientDto.getNotificationAddress());
        client.setNotificationAddressId(idNotiAddress);
        clientMapper.insert(client);

        ClientDto dto = toDto.clientToDto(client);
        Country country = countryMapper.getCountry(idCountry);
        CountryDto countryDto = toDto.countryToDto(country);
        dto.setCountry(countryDto);

        Address address = addressMapper.getAddress(idAddress);
        AddressDto addressDto = toDto.addressToDto(address);
        dto.setAddress(addressDto);

        dto.setNotificationAddress(notiAddressDto);

        return dto;
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
        checkForDuplicates(clientDto);
        validateCountry(clientDto.getCountry());
        validateAddress(clientDto.getAddress());
        validateAddress(clientDto.getNotificationAddress());
        AddressDto notiAddressDto = validateEqualsAddresses(clientDto.getAddress(), clientDto.getNotificationAddress());

        Client client = toEntity.clientToEntity(clientDto);
        String idCountry = countryService.getIdCountry(clientDto.getCountry());
        client.setCountryId(idCountry);
        Long idAddress = addressService.getIdAddress(clientDto.getAddress());
        client.setAddressId(idAddress);
        Long idNotiAddress = addressService.getIdAddress(clientDto.getNotificationAddress());
        client.setNotificationAddressId(idNotiAddress);
        client.setScoring(clientMapper.recoverScoring(clientDto.getId()));
        clientMapper.updateClient(client);

        ClientDto dto = toDto.clientToDto(client);
        Country country = countryMapper.getCountry(idCountry);
        CountryDto countryDto = toDto.countryToDto(country);
        dto.setCountry(countryDto);

        Address address = addressMapper.getAddress(idAddress);
        AddressDto addressDto = toDto.addressToDto(address);
        dto.setAddress(addressDto);

        dto.setNotificationAddress(notiAddressDto);

        return dto;
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

    private AddressDto validateEqualsAddresses(AddressDto address, AddressDto notificationAddress) {

        if (address.getId() == notificationAddress.getId() && address.getCity().equals(notificationAddress.getCity()) && address.getZipCode().equals(notificationAddress.getZipCode()) && address.getStreet().equals(notificationAddress.getStreet())) {
            return address;
        } else {
            return notificationAddress;
        }
    }

    private void validateCountry(CountryDto country) {
        if (country.getIsoA2() == null) {
            throw new InvalidClientDtoException("El ID del país no puede ser nulo");
        }

        if (!countryMapper.existsByISOA2(country.getIsoA2())) {
            throw new DBException("El país añadido no existe en la base de datos");
        }
    }

    private void validateAddress(AddressDto address) {
        if (address.getCity() == null && address.getStreet() == null) {
            throw new InvalidClientDtoException("La dirección no puede ser nulo");
        }
        if (!addressMapper.existsByCity(address.getCity())) {
            throw new DBException("La dirección añadida no existe en la base de datos");
        }
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
        if (!nif.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            throw new AttributeException("El NIF no tiene el formato correcto");
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
        if (clientMapper.existsByNif(clientDto.getNif()) > 1) {
            throw new DuplicateModel("Ya existe un cliente con este NIF: " + clientDto.getNif());
        }

        if (clientMapper.existsByEmail(clientDto.getEmail()) > 1) {
            throw new DuplicateModel("Ya existe un cliente con este email: " + clientDto.getEmail());
        }
        if (clientMapper.existsByPhone(clientDto.getPhone()) > 1) {
            throw new DuplicateModel("Ya existe un cliente con este teléfono: " + clientDto.getPhone());
        }
    }

    private void validateScoring(Integer scoring) {
        if (scoring == null || scoring < 0) {
            throw new InvalidClientDtoException("El scoring no puede ser nulo ni negativo");
        }
    }

}