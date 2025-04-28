package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.exceptions.notfound.EconomicDataNotFoundException;
import com.helloworld.renting.mapper.EconomicDataMapper;
import org.springframework.stereotype.Service;

@Service
public class EconomicDataService {

    private EconomicDataMapper economicDataMapper;

    public EconomicDataService(EconomicDataMapper economicDataMapper) {
        this.economicDataMapper = economicDataMapper;
    }

    public void deleteEconomicDataFromClient(Long id) {
        if (noEconomicDataExists(id)) {
            throw new EconomicDataNotFoundException("No economic data found for client");
        }

        economicDataMapper.deleteEconomicDataEmployedByClientId(id);
        economicDataMapper.deleteEconomicDataSelfEmployedByClientId(id);
    }

    private boolean noEconomicDataExists(Long id) {
        return economicDataMapper.getEconomicDataEmployedByClientId(id).isEmpty() &&
                economicDataMapper.getEconomicDataSelfEmployedByClientId(id).isEmpty();
    }

}
