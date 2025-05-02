package com.helloworld.renting.service.country;

import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.mapper.*;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final ClientMapper clientMapper;
    private final StructMapperToDto toDto;
    private final StructMapperToEntity toEntity;
    private final CountryMapper countryMapper;

    public CountryService(ClientMapper clientMapper, StructMapperToDto toDto, StructMapperToEntity toEntity, CountryMapper countryMapper, AddressMapper addressMapper) {
        this.clientMapper = clientMapper;
        this.toDto = toDto;
        this.toEntity = toEntity;
        this.countryMapper = countryMapper;
    }

    public String getIdCountry(CountryDto country) {
        return countryMapper.IDByISOA2(country.getIsoA2());
    }

}
