package com.helloworld.renting.service.request.approval.rules.approved.scoringBelowThresholdRule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scoring-threshold")
public class ScoringBelowThresholdProperties {
    private int scoringThreshold;
    private int minScoring;
    private int maxScoring;

    public int getMaxScoring() {
        return maxScoring;
    }

    public void setMaxScoring(int maxScoring) {
        this.maxScoring = maxScoring;
    }

    public int getMinScoring() {
        return minScoring;
    }

    public void setMinScoring(int minScoring) {
        this.minScoring = minScoring;
    }

    public int getScoringThreshold() {
        return scoringThreshold;
    }

    public void setScoringThreshold(int scoringThreshold) {
        this.scoringThreshold = scoringThreshold;
    }
}
