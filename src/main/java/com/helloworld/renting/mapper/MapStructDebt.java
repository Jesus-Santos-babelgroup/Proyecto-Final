package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.entities.Debt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructDebt {
    Debt toEntity(Debt dto);

    DebtDto toDto(Debt entity);
}
