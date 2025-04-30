package com.helloworld.renting.service.request.approval.rules.approved.investmentexceedsnet;

import com.helloworld.renting.dto.RentingRequestDto;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface InvestmentExceedsNetMapper {
    @Select("""
              SELECT
                e.Net_income           AS netIncomeEmployed,
              FROM renting_request r
              JOIN client c ON c.ID_client = r.ID_client
              LEFT JOIN economic_data_employed e ON e.ID_client = c.ID_client
              WHERE r.ID_request = #{requestId}
            """)
    BigDecimal getNetIncomeEmployed(@Param("requestId") Long requestId);

    @Select("""
              SELECT
                e.Net_income           AS netIncomeSelfEmployed,
              FROM renting_request r
              JOIN client c ON c.ID_client = r.ID_client
              LEFT JOIN economic_data_self_employed s ON s.ID_client = c.ID_client
              WHERE r.ID_request = #{requestId}
            """)
    BigDecimal getNetIncomeSelfEmployed(@Param("requestId") Long requestId);
}
