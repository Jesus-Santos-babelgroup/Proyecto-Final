package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.RentingRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructRequest {
    RentingRequest toEntity(RentingRequestDto dto);

    RentingRequestDto toDto(RentingRequest entity);
}
