package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Debt;
import com.helloworld.renting.exceptions.attributes.InvalidRentingRequestDtoException;
import com.helloworld.renting.mapper.DebtMapper;
import com.helloworld.renting.mapper.MapStructDebt;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DebtAmountLessThanMonthlyQuote implements DenialRule {

    private final DebtMapper debtMapper;
    private final MapStructDebt mapStructDebt;

    public DebtAmountLessThanMonthlyQuote(DebtMapper debtMapper, MapStructDebt mapStructDebt) {
        this.debtMapper = debtMapper;
        this.mapStructDebt = mapStructDebt;
    }

    @Override
    public boolean conditionMet(RentingRequestDto requestDto) {
        validate(requestDto);
        List<Debt> listDebts = debtMapper.findDebtsByNif(requestDto.getClient().getNif());
        List<DebtDto> listDebtsDto = mapDebtListToDtos(listDebts);
        if (listDebtsDto.isEmpty()) {
            return true;
        }

        BigDecimal monthlyQuota = requestDto.getQuotaFinal();
        BigDecimal totalDebt = BigDecimal.ZERO;
        for (DebtDto debt : listDebtsDto) {
            if (debt.getAmount() == null) {
                throw new InvalidRentingRequestDtoException("La deuda no puede ser null.");
            }
            if (debt.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new InvalidRentingRequestDtoException("La deuda no puede ser negativa.");
            }
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
            throw new InvalidRentingRequestDtoException("Cuota mensual no puede ser null.");
        } else if (requestDto.getClient().getNif() == null) {
            throw new InvalidRentingRequestDtoException("Lista de deudas no puede ser null.");
        }
    }

    private List<DebtDto> mapDebtListToDtos(List<Debt> listDebts) {
        List<DebtDto> listDebtsDto = new ArrayList<>();
        for (Debt debt : listDebts) {
            listDebtsDto.add(mapStructDebt.toDto(debt));
        }
        return listDebtsDto;
    }
}
