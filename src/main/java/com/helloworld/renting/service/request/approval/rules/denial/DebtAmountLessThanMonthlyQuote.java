package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.exceptions.attributes.InvalidRulesContextDtoException;
import com.helloworld.renting.mapper.DebtMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class DebtAmountLessThanMonthlyQuote implements DenialRule {

    private final DebtMapper debtMapper;

    public DebtAmountLessThanMonthlyQuote(DebtMapper debtMapper) {
        this.debtMapper = debtMapper;
    }

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {
        validate(requestDto);
        List<Debt> listDebts = debtMapper.findDebtsByNif(requestDto.getClient().getNif());
        if (listDebts.isEmpty()) {
            return true;
        }

        BigDecimal monthlyQuota = requestDto.getQuotaFinal();
        BigDecimal totalDebt = BigDecimal.ZERO;
        for (Debt debt : listDebts) {
            totalDebt = totalDebt.add(debt.getAmount());
        }

        return monthlyQuota.compareTo(totalDebt) > 0;
    }

    @Override
    public String getName() {
        return "Holder total debt is less than monthly quota";
    }

    private void validate(RentingRequestDto requestDto) {
        Objects.requireNonNull(requestDto, "RulesContextDto no puede ser null");

        if (requestDto.getQuotaFinal() == null) {
            throw new InvalidRulesContextDtoException("Cuota mensual no puede ser null.");
        } else if (requestDto.getClient().getNif() == null) {
            throw new InvalidRulesContextDtoException("Lista de deudas no puede ser null.");
        }
    }
}
