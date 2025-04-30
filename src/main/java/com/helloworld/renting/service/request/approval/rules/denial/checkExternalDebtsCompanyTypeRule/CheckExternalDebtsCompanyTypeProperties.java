package com.helloworld.renting.service.request.approval.rules.denial.checkExternalDebtsCompanyTypeRule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "rules.debts")
public class CheckExternalDebtsCompanyTypeProperties {

    private List<String> invalidCategories;

    public List<String> getInvalidCategories() {
        return invalidCategories;
    }

    public void setInvalidCategories(List<String> invalidCategories) {
        this.invalidCategories = invalidCategories;
    }
}

