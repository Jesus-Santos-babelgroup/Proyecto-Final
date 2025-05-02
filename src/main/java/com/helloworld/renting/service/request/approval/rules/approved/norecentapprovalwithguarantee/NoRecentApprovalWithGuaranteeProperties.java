package com.helloworld.renting.service.request.approval.rules.approved.norecentapprovalwithguarantee;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rules.previous-approval-with-guarantees")
public class NoRecentApprovalWithGuaranteeProperties {

    private int years;

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
