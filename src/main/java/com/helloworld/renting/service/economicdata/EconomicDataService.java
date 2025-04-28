package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.EconomicDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EconomicDataService {

    private EconomicDataMapper economicDataMapper;
    private Logger logger = LoggerFactory.getLogger(EconomicDataService.class);

    public EconomicDataService(EconomicDataMapper economicDataMapper) {
        this.economicDataMapper = economicDataMapper;
    }

    public void deleteEconomicDataFromClient(Long id) {
        if (noEconomicDataExists(id)) {
            logger.warn("No economic data found for client");
            throw new EconomicDataNotFoundException("No economic data found for client");
        }

        try {
            logger.debug("Deleting economic data for client");
            economicDataMapper.deleteEconomicDataEmployedByClientId(id);
            economicDataMapper.deleteEconomicDataSelfEmployedByClientId(id);
        } catch (Exception e) {
            logger.error("Error deleting economic data from client");
            throw new DBException("Error deleting economic data from client");
        }
    }

    private boolean noEconomicDataExists(Long id) {
        try {
            return economicDataMapper.getEconomicDataEmployedByClientId(id).isEmpty() &&
                    economicDataMapper.getEconomicDataSelfEmployedByClientId(id).isEmpty();
        } catch (Exception e) {
            logger.error("Error checking if economic data exists for client");
            throw new DBException("Error checking if economic data exists for client");
        }
    }

}
