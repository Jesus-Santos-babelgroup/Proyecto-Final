package com.helloworld.renting.service.request.baseCuota;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cuota")
public class BaseCuotaProperties {

    private int baseMeses;

    public int getBaseMeses() {
        return baseMeses;
    }

    public void setBaseMeses(int baseMeses) {
        this.baseMeses = baseMeses;
    }
}
