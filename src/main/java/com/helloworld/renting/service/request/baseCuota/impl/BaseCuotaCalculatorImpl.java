package com.helloworld.renting.service.request.baseCuota.impl;

import com.helloworld.renting.service.request.baseCuota.BaseCuotaCalculator;

public class BaseCuotaCalculatorImpl implements BaseCuotaCalculator {
    @Override
    public Double getBaseCuota(int months) {
        Double baseCuota = 1.0;
        if(months == 12)
            return baseCuota;
        else if(months < 12)
            return baseCuota + ((12 - months) * 0.1);
        else
            return Math.max(baseCuota - ((months - 12) * 0.03), 0.8);
    }

}
