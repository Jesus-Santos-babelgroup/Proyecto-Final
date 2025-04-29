package com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "rules.total-investment")
public class MaxTotalInvestmentProperties {

    private BigDecimal limit;

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
}
