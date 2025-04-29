package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.dto.InformaDto;
import com.helloworld.renting.dto.RulesContextDto;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InformaDataApprovalRuleTest {


    private final InformaDataApprovalRule rule = new InformaDataApprovalRule();

    private InformaDto createInforma(int fiscalYear, BigDecimal profitBeforeTax) {
        InformaDto informa = new InformaDto();
        informa.setCif("B12345678");
        informa.setCompanyName("Empresa S.A.");
        informa.setMunicipality("Madrid");
        informa.setZipCode("28001");
        informa.setAmountSales(new BigDecimal("500000"));
        informa.setFiscalYear(fiscalYear);
        informa.setProfitBeforeTax(profitBeforeTax);
        return informa;
    }

    int currentYear = LocalDate.now().getYear();

    @Test
    void testInformaDataApprovalRuleValidData() {
        RulesContextDto context = new RulesContextDto();
        context.setNetIncomeEmployed(new BigDecimal("2000"));

        context.setInformaRecords(List.of(
                createInforma(currentYear, new BigDecimal("160000")),
                createInforma(2024, new BigDecimal("155000")),
                createInforma(2023, new BigDecimal("150000"))
        ));

        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testInformaDataApprovalRuleValidDataOlderThanTwoYears() {
        RulesContextDto context = new RulesContextDto();
        context.setNetIncomeEmployed(new BigDecimal("2000"));

        context.setInformaRecords(List.of(
                createInforma(2022, new BigDecimal("160000")),
                createInforma(2021, new BigDecimal("155000")),
                createInforma(2019, new BigDecimal("150000"))
        ));

        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testInformaDataApprovalRuleSelfEmployed() {
        RulesContextDto context = new RulesContextDto();
        context.setNetIncomeEmployed(BigDecimal.ZERO);

        assertTrue(rule.conditionMet(context));
    }

    @Test
    void testInformaDataApprovalRuleLessThan150k() {
        RulesContextDto context = new RulesContextDto();
        context.setNetIncomeEmployed(new BigDecimal("2000"));

        context.setInformaRecords(List.of(
                createInforma(currentYear, new BigDecimal("100000")),
                createInforma(2024, new BigDecimal("120000")),
                createInforma(2023, new BigDecimal("90000"))
        ));

        assertFalse(rule.conditionMet(context));
    }

    @Test
    void testInformaDataApprovalRuleNoInformaRecord() {
        RulesContextDto context = new RulesContextDto();
        context.setNetIncomeEmployed(new BigDecimal("2000"));
        context.setInformaRecords(Collections.emptyList());

        assertThrows(InvalidRulesContextDtoException.class, () -> rule.conditionMet(context));
    }


}
