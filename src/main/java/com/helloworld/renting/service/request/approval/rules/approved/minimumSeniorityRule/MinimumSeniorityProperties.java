package com.helloworld.renting.service.request.approval.rules.approved.minimumSeniorityRule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "seniority-rule")
public class MinimumSeniorityProperties {

    private int requiredYears;

    public int getRequiredYears() {
        return requiredYears;
    }

    public void setRequiredYears(int requiredYears) {
        this.requiredYears = requiredYears;
    }
}