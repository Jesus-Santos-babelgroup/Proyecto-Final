package com.helloworld.renting.service.request.approval.rules.approved;


import com.helloworld.renting.dto.InformaDto;
import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class InformaDataApprovalRule extends ApprovedRule {

    private static final Logger logger = LoggerFactory.getLogger(InformaDataApprovalRule.class);

    @Override
    public boolean conditionMet(RulesContextDto rulesContextDto) {
        BigDecimal employeeNetIncome = rulesContextDto.getNetIncomeEmployed();
        if (employeeNetIncome == null || employeeNetIncome.compareTo(BigDecimal.ZERO) <= 0) {
            logger.debug("Client is not al employee, skipping InformaDataApprovalRule.");
            return true;
        }

        List<InformaDto> informaRegistry = rulesContextDto.getInformaRecords();
        if (informaRegistry == null || informaRegistry.isEmpty()) {
            logger.warn("Employer Informa recordos are missing");
            throw new InvalidRulesContextDtoException("Employer informa records are missing");
        }

        if (isDataOutdated(informaRegistry)) {
            logger.debug("Employer informa is outdated.");
            return false;
        }

        double averageProfit3LatestYears = calculateAverageProfit3LatestYears(informaRegistry);
        logger.debug("Employer average profit before taxes: {}", averageProfit3LatestYears);
        return averageProfit3LatestYears > 150000;

    }

    @Override
    public String getName() {
        return ("InformaDataApprovalRule");
    }

    private boolean isDataOutdated(List<InformaDto> informaRecords) {
        int latestYear = informaRecords.stream()
                .mapToInt(InformaDto::getFiscalYear)
                .max()
                .orElse(0);
        return latestYear < LocalDate.now().getYear() - 2;
    }

    private double calculateAverageProfit3LatestYears(List<InformaDto> informaRecords) {
        return informaRecords.stream()
                .sorted(Comparator.comparing(InformaDto::getFiscalYear).reversed())
                .limit(3)
                .mapToDouble(informa -> informa.getProfitBeforeTax().doubleValue())
                .average()
                .orElse(0);
    }
}
