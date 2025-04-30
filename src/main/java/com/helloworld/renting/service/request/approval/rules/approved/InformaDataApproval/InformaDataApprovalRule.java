package com.helloworld.renting.service.request.approval.rules.approved.InformaDataApproval;


import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class InformaDataApprovalRule implements ApprovedRule {

    private static final Logger logger = LoggerFactory.getLogger(InformaDataApprovalRule.class);

    private InformaDataApprovalMapper mapper;

    @Autowired
    public InformaDataApprovalRule(InformaDataApprovalMapper informaMapper) {
        this.mapper = informaMapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {

        long idClient = requestDto.getClient().getId();
        String cif = mapper.getCifByClientID(idClient);

        if (cif == null) {
            logger.debug("Client is not an employee, skipping InformaDataApprovalRule.");
            return true;
        }

        int minYear = LocalDate.now().getYear() - 2;

        List<BigDecimal> profits = mapper.findProfitLast3YearsByCif(cif, minYear);

        if (profits == null || profits.isEmpty()) {
            logger.warn("No Informa data found for CIF {}", cif);
            return false;
        }

        double averageProfit = profits.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0);


        logger.debug("Employer average profit before taxes ({}): {}", cif, averageProfit);
        return averageProfit > 150000;

    }

    @Override
    public String getName() {
        return ("InformaDataApprovalRule");
    }


}
