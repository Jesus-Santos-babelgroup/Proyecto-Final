package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "rules.investment")
public class InvestmentUnderGrossIncomeThresholdProperties {

    private int multiplier;

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
