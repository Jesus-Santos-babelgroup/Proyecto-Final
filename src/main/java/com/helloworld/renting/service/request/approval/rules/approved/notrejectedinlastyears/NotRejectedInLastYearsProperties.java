package com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rules.last-years-not-rejected")
public class NotRejectedInLastYearsProperties {
    private int years;

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
