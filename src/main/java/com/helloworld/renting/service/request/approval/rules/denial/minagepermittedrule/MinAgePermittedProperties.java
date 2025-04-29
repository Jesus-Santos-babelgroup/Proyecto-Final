package com.helloworld.renting.service.request.approval.rules.denial.minagepermittedrule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rules.minimum-age-permitted")
public class MinAgePermittedProperties {

    private int minimumAge;

    public int getMinimumAge() {
        return this.minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

}
