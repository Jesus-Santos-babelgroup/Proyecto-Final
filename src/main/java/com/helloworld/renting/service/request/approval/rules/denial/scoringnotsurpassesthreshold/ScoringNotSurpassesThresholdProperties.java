package com.helloworld.renting.service.request.approval.rules.denial.scoringnotsurpassesthreshold;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "scoring-threshold")
public class ScoringNotSurpassesThresholdProperties {
    @Value("${rules.denial-rule.scoring-threshold}")
    private int scoringThreshold;
}

