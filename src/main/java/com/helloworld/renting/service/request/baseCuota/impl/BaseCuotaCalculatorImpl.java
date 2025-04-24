package com.helloworld.renting.service.request.baseCuota.impl;

import com.helloworld.renting.service.request.baseCuota.BaseCuotaCalculator;
import com.helloworld.renting.service.request.baseCuota.BaseCuotaProperties;
import org.springframework.stereotype.Component;

@Component
public class BaseCuotaCalculatorImpl implements BaseCuotaCalculator {

    private final BaseCuotaProperties cuotaProperties;

    public BaseCuotaCalculatorImpl(BaseCuotaProperties cuotaProperties) {
        this.cuotaProperties = cuotaProperties;
    }

    @Override
    public Double getBaseCuota(int months) {
        int baseMeses = cuotaProperties.getBaseMeses();
        Double baseCuota = 1.0;

        if(months == baseMeses)
            return baseCuota;
        else if(months < baseMeses)
            return baseCuota + ((baseMeses - months) * 0.1);
        else
            return Math.max(baseCuota - ((months - baseMeses) * 0.03), 0.8);
    }
}
