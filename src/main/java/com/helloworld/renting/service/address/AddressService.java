package com.helloworld.renting.service.address;

import com.helloworld.renting.dto.AddressDto;
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

}
