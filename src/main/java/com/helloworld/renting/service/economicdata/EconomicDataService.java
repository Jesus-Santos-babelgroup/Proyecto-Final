package com.helloworld.renting.service.economicData;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
import com.helloworld.renting.mapper.economicData.EconomicDataEmployedMapper;
import com.helloworld.renting.mapper.economicData.EconomicDataSelfEmployedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class EconomicDataService {

    private final EconomicDataSelfEmployedMapper economicDataSelfEmployedMapper;
    private final EconomicDataEmployedMapper economicDataEmployedMapper;
    private final ClientMapper clientMapper;
    private final StructMapperToDto structMapperToDto;
    private final StructMapperToEntity structMapperToEntity;
    private Logger logger = LoggerFactory.getLogger(EconomicDataService.class);

    public EconomicDataService(EconomicDataSelfEmployedMapper economicDataSelfEmployedMapper,
                               EconomicDataEmployedMapper economicDataEmployedMapper,
                               ClientMapper clientMapper,
                               StructMapperToDto structMapperToDto,
                               StructMapperToEntity structMapperToEntity) {
        this.economicDataSelfEmployedMapper = economicDataSelfEmployedMapper;
        this.economicDataEmployedMapper = economicDataEmployedMapper;
        this.clientMapper = clientMapper;
        this.structMapperToDto = structMapperToDto;
        this.structMapperToEntity = structMapperToEntity;
    }

    @Transactional
    public EconomicDataSelfEmployedDto addEconomicDataSelfEmployed(
            EconomicDataSelfEmployedDto economicDataSelfEmployedDto,
            Long clientId) {

        checkIfClientExist(clientId);
        Client client = clientMapper.findById(clientId);
        economicDataSelfEmployedDto.setClient(structMapperToDto.clientToDto(client));

        checkDuplicateYearSelfEmployed(clientId, economicDataSelfEmployedDto.getYearEntry());

        EconomicDataSelfEmployed economicDataSelfEmployed =
                structMapperToEntity.economicalDataSelfEmployedToEntity(economicDataSelfEmployedDto);

        economicDataSelfEmployedMapper.insert(economicDataSelfEmployed);

        return structMapperToDto.economicalDataSelfEmployedToDto(economicDataSelfEmployed);
    }

    @Transactional
    public EconomicDataEmployedDto addEconomicDataEmployed(
            EconomicDataEmployedDto economicDataEmployedDto,
            Long clientId) {

        checkIfClientExist(clientId);
        Client client = clientMapper.findById(clientId);
        economicDataEmployedDto.setClient(structMapperToDto.clientToDto(client));

        checkDuplicateYearEmployed(clientId, economicDataEmployedDto.getYearEntry());
        checkDateNotInFuture(economicDataEmployedDto.getStartDate());
        checkDateNotInFuture(economicDataEmployedDto.getEndDate());
        checkStartDateMatchesYearEntry(
                economicDataEmployedDto.getStartDate(),
                economicDataEmployedDto.getYearEntry()
        );

        EconomicDataEmployed economicDataEmployed =
                structMapperToEntity.economicalDataEmployedToEntity(economicDataEmployedDto);

        economicDataEmployedMapper.insert(economicDataEmployed);

        return structMapperToDto.economicalDataEmployedToDto(economicDataEmployed);
    }


    private void checkDuplicateYearSelfEmployed(Long clientId, Integer yearEntry) {
        if (economicDataSelfEmployedMapper.existsSelfEmployedByClientIdAndYear(clientId, yearEntry)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe información de autónomo para este cliente en el año " + yearEntry);
        }
    }

    private void checkDuplicateYearEmployed(Long clientId, Integer yearEntry) {
        if (economicDataEmployedMapper.existsEmployedByClientIdAndYear(clientId, yearEntry)) {
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

    @Transactional
    public void deleteEconomicDataEmployedFromClient(Long id) {
        if (noEconomicDataEmployedExists(id)) {
            throw new EconomicDataNotFoundException("No economic data employed found for client");
        }

        try {
            logger.debug("Deleting economic data for client");
            economicDataEmployedMapper.deleteEconomicDataEmployedByClientId(id);
        } catch (Exception e) {
            throw new DBException("Error deleting economic data employed from client");
        }
    }

    private boolean noEconomicDataEmployedExists(Long id) {
        try {
            return economicDataEmployedMapper.getEconomicDataEmployedByClientId(id).isEmpty();
        } catch (Exception e) {
            throw new DBException("Error checking if economic data employed exists for client");
        }
    }

    @Transactional
    public void deleteEconomicDataSelfEmployedFromClient(Long id) {
        if (noEconomicDataSelfEmployedExists(id)) {
            throw new EconomicDataNotFoundException("No economic data self employed found for client");
        }

        try {
            logger.debug("Deleting economic data self employed for client");
            economicDataSelfEmployedMapper.deleteEconomicDataSelfEmployedByClientId(id);
        } catch (Exception e) {
            throw new DBException("Error deleting economic data self employed from client");
        }
    }

    private boolean noEconomicDataSelfEmployedExists(Long id) {
        try {
            return economicDataSelfEmployedMapper.getEconomicDataSelfEmployedByClientId(id).isEmpty();
        } catch (Exception e) {
            throw new DBException("Error checking if economic data self employed exists for client");
        }
    }

}
