package com.helloworld.renting.service.request.approval.rules.denial;
import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class AgeClientPlusContractTerm implements DenialRule {


        private static final Logger logger = LoggerFactory.getLogger(AgeClientPlusContractTerm.class);

        public boolean conditionMet(RulesContextDto context) {
            LocalDate birthDate = context.getClientBirthDate();
            LocalDate contractDate = context.getContractingDate();

            if (birthDate == null || contractDate == null) {
                logger.warn("Client birth date or contract date is not valid");
                throw new AttributeException("Client birth date or contract date is not valid");
            } else {
                logger.debug("AgeClientPlusContractTermRule checked");

                int clientAge = calculateAge(birthDate, contractDate);
                int contractTermInYears = calculateContractTerm(contractDate);

                int totalYears = clientAge + contractTermInYears;

                return totalYears <= 80;
            }
        }

        public String getName() {
            return "AgeClientPlusContractTermRule";
        }

        private int calculateAge(LocalDate birthDate, LocalDate contractDate) {
            return contractDate.getYear() - birthDate.getYear() - (contractDate.getDayOfYear() < birthDate.getDayOfYear() ? 1 : 0);
        }


        private int calculateContractTerm(LocalDate contractDate) {
            LocalDate currentDate = LocalDate.now();
            return currentDate.getYear() - contractDate.getYear();
        }
    }

