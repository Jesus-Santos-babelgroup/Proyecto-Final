package com.helloworld.renting.service.address;

import com.helloworld.renting.dto.AddressDto;
import com.helloworld.renting.entities.Address;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.mapper.*;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto toDto;
    private final StructMapperToEntity toEntity;
    private final CountryMapper countryMapper;
    private final AddressMapper addressMapper;

    public AddressService(ClientMapper clientMapper, StructMapperToDto toDto, StructMapperToEntity toEntity, CountryMapper countryMapper, AddressMapper addressMapper) {
        this.clientMapper = clientMapper;
        this.toDto = toDto;
        this.toEntity = toEntity;
        this.countryMapper = countryMapper;
        this.addressMapper = addressMapper;
    }

    public Long getIdAddress(AddressDto address) {
        return addressMapper.IdByCity(address.getCity());
    }

    public Long getIdNotificationAddress(AddressDto address) {
        if (address != null) {
            return addressMapper.IdByCity(address.getCity());
        }
        return null;
    }

    public Long findOrCreateAddress(AddressDto addressDto) {
        if (addressDto == null) {
            throw new InvalidClientDtoException("La dirección no puede ser nula");
        }

        if (addressDto.getCity() == null || addressDto.getZipCode() == null) {
            throw new InvalidClientDtoException("La ciudad y el código postal son obligatorios");
        }

        // Buscamos si existe una dirección con estos detalles
        Long addressId = addressMapper.findAddressByDetails(
                addressDto.getCity(),
                addressDto.getZipCode(),
                addressDto.getStreet());

        // Si no existe, creamos una nueva
        if (addressId == null) {
            Address address = toEntity.addressToEntity(addressDto);
            addressMapper.insertAddress(address);
            return address.getId();
        }

        return addressId;
    }

    public boolean areAddressesEqual(AddressDto address1, AddressDto address2) {
        if (address1 == null || address2 == null) {
            return false;
        }

        return address1.getCity().equals(address2.getCity()) &&
                address1.getZipCode().equals(address2.getZipCode()) &&
                ((address1.getStreet() == null && address2.getStreet() == null) ||
                        (address1.getStreet() != null && address1.getStreet().equals(address2.getStreet())));
    }

    public Long getIdAddressSafe(AddressDto address) {
        if (address == null) {
            return null;
        }

        // Primero buscamos por criterios más específicos
        Long addressId = addressMapper.findAddressIdByCityAndDetails(
                address.getCity(),
                address.getZipCode(),
                address.getStreet());

        // Si no encontramos, usamos o creamos una dirección
        if (addressId == null) {
            return findOrCreateAddress(address);
        }

        return addressId;
    }
}
