package com.helloworld.renting.service.economicdata;

import com.helloworld.renting.mapper.EconomicDataMapper;
import org.springframework.stereotype.Service;

@Service
public class EconomicDataService {

    private EconomicDataMapper economicDataMapper;

    public EconomicDataService(EconomicDataMapper economicDataMapper) {
        this.economicDataMapper = economicDataMapper;
    }

    public void deleteClient(Long id) {
        
    }

}
