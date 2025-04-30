package com.helloworld.renting.service.request.approval.rules.approved.minimumSeniorityRule;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.mapper.economicalData.EconomicalDataEmployedMapper;
import com.helloworld.renting.service.request.approval.rules.approved.ApprovedRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MinimumSeniorityRule implements ApprovedRule {

    private final MinimumSeniorityProperties seniorityRuleProperties;
    private final EconomicalDataEmployedMapper economicalDataEmployedMapper;

    @Autowired
    public MinimumSeniorityRule(MinimumSeniorityProperties seniorityRuleProperties,
                                EconomicalDataEmployedMapper economicalDataEmployedMapper) {
        this.seniorityRuleProperties = seniorityRuleProperties;
        this.economicalDataEmployedMapper = economicalDataEmployedMapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto rentingRequestDto) {
        ClientDto client = rentingRequestDto.getClient();
        List<EconomicDataEmployed> economicDataList = economicalDataEmployedMapper.findAllByClientId(client.getId());
        if (economicDataList == null ||economicDataList.isEmpty()){
            return true;
        }else{
            for (EconomicDataEmployed economicData:economicDataList){
                int seniority = getSeniority(economicData);
                if(seniority >= seniorityRuleProperties.getRequiredYears()){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String getName() {
        return "MinimumSeniorityRule";
    }

    private int getSeniority(EconomicDataEmployed economicData){
        return economicData.getEndDate().getYear() - economicData.getStartDate().getYear();
    }
}