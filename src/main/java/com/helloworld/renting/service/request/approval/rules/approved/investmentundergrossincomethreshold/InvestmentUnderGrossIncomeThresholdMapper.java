package com.helloworld.renting.service.request.approval.rules.approved.investmentundergrossincomethreshold;

import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface InvestmentUnderGrossIncomeThresholdMapper {

    @Select("""
            SELECT
              ID_nonpayment        AS id,
              ID_client            AS clientId,
              Category             AS category,
              Start_year           AS startYear,
              Payment_amount       AS paymentAmount,
              Initial_total_import AS initialTotalImport
            FROM nonpayment
            WHERE ID_client = #{clientId}
            """)
    List<EconomicDataSelfEmployedDto> findByClientId(@Param("clientId") Long clientId);

    @Select("""
        SELECT SUM(v.Investment) AS TotalInvestment
        FROM Renting_request rr
        JOIN Vehicle_requested vr ON rr.ID_request = vr.ID_request
        JOIN Vehicle v ON vr.ID_vehicle = v.ID_vehicle
        WHERE rr.ID_request = #{requestId}
        """)
    BigDecimal findTotalInvestment(@Param("requestId") Long requestId);
}
