package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.EconomicDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EconomicDataService {

    private EconomicDataMapper economicDataMapper;
    private Logger logger = LoggerFactory.getLogger(EconomicDataService.class);

    public EconomicDataService(EconomicDataMapper economicDataMapper) {
        this.economicDataMapper = economicDataMapper;
    }

    @Transactional
    public void deleteEconomicDataEmployedFromClient(Long id) {
        if (noEconomicDataEmployedExists(id)) {
            throw new EconomicDataNotFoundException("No economic data employed found for client");
        }

        try {
            logger.debug("Deleting economic data for client");
            economicDataMapper.deleteEconomicDataEmployedByClientId(id);
        } catch (Exception e) {
            throw new DBException("Error deleting economic data employed from client");
        }
    }

    private boolean noEconomicDataEmployedExists(Long id) {
        try {
            return economicDataMapper.getEconomicDataEmployedByClientId(id).isEmpty();
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
            economicDataMapper.deleteEconomicDataSelfEmployedByClientId(id);
        } catch (Exception e) {
            throw new DBException("Error deleting economic data self employed from client");
        }
    }

    private boolean noEconomicDataSelfEmployedExists(Long id) {
        try {
            return economicDataMapper.getEconomicDataSelfEmployedByClientId(id).isEmpty();
        } catch (Exception e) {
            throw new DBException("Error checking if economic data self employed exists for client");
        }
    }

}
