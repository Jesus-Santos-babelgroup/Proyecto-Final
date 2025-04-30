package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.economicalData.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class EconomicDataService {

    private final EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper;
    private final EconomicalDataEmployedMapper economicalDataEmployedMapper;
    private final StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto;
    private final StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity;
    private final StructEconomicalDataEmployedMapperToDto employedMapperToDto;
    private final StructEconomicalDataEmployedMapperToEntity employedMapperToEntity;
    private final ClientMapper clientMapper;

    public EconomicDataService(EconomicalDataSelfEmployedMapper economicalDataSelfEmployedMapper,
                               EconomicalDataEmployedMapper economicalDataEmployedMapper,
                               StructEconomicalDataSelfEmployedMapperToDto selfEmployedMapperToDto,
                               StructEconomicalDataSelfEmployedMapperToEntity selfEmployedMapperToEntity,
                               StructEconomicalDataEmployedMapperToDto employedMapperToDto,
                               StructEconomicalDataEmployedMapperToEntity employedMapperToEntity,
                               ClientMapper clientMapper){
        this.economicalDataSelfEmployedMapper = economicalDataSelfEmployedMapper;
        this.economicalDataEmployedMapper = economicalDataEmployedMapper;
        this.selfEmployedMapperToDto = selfEmployedMapperToDto;
        this.selfEmployedMapperToEntity = selfEmployedMapperToEntity;
        this.employedMapperToDto = employedMapperToDto;
        this.employedMapperToEntity = employedMapperToEntity;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public EconomicDataSelfEmployedDto addEconomicDataSelfEmployed(
            EconomicDataSelfEmployedDto economicDataSelfEmployedDto,
            Long clientId) {

        checkIfClientExist(clientId);
        economicDataSelfEmployedDto.setClientId(clientId);

        checkDuplicateYearSelfEmployed(clientId, economicDataSelfEmployedDto.getYearEntry());

        EconomicDataSelfEmployed economicDataSelfEmployed =
                selfEmployedMapperToEntity.toEntity(economicDataSelfEmployedDto);

        economicalDataSelfEmployedMapper.insert(economicDataSelfEmployed);

        return selfEmployedMapperToDto.toDto(economicDataSelfEmployed);
    }

    @Transactional
    public EconomicDataEmployedDto addEconomicDataEmployed(
            EconomicDataEmployedDto economicDataEmployedDto,
            Long clientId) {

        checkIfClientExist(clientId);
        economicDataEmployedDto.setClientId(clientId);

        checkDuplicateYearEmployed(clientId, economicDataEmployedDto.getYearEntry());
        checkDateNotInFuture(economicDataEmployedDto.getStartDate());
        checkDateNotInFuture(economicDataEmployedDto.getEndDate());
        checkStartDateMatchesYearEntry(
                economicDataEmployedDto.getStartDate(),
                economicDataEmployedDto.getYearEntry()
        );

        EconomicDataEmployed economicDataEmployed =
                employedMapperToEntity.toEntity(economicDataEmployedDto);

        economicalDataEmployedMapper.insert(economicDataEmployed);

        return employedMapperToDto.toDto(economicDataEmployed);
    }


    private void checkDuplicateYearSelfEmployed(Long clientId, Integer yearEntry) {
        if (economicalDataSelfEmployedMapper.existsSelfEmployedByClientIdAndYear(clientId, yearEntry)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe información de autónomo para este cliente en el año " + yearEntry);
        }
    }

    private void checkDuplicateYearEmployed(Long clientId, Integer yearEntry) {
        if (economicalDataEmployedMapper.existsEmployedByClientIdAndYear(clientId, yearEntry)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe información de asalariado para este cliente en el año " + yearEntry);
        }
    }

    private void checkDateNotInFuture(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha no puede estar en el futuro");
        }
    }

    private void checkStartDateMatchesYearEntry(LocalDate startDate, Integer yearEntry) {
        if (startDate.getYear() != yearEntry) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El año de entrada debe coincidir con el año de la fecha de inicio");
        }
    }

    private void checkIfClientExist(Long clientId){
        if(!clientMapper.existsById(clientId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe");
        }
    }

}
