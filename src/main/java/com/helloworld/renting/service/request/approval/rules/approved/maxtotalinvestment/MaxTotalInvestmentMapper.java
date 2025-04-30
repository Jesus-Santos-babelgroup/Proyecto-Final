package com.helloworld.renting.service.request.approval.rules.approved.maxtotalinvestment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;

@Mapper
public interface MaxTotalInvestmentMapper {

    @Select("""
              SELECT SUM(v.Investment) AS TotalInvestment
              FROM Renting_request rr
              JOIN Vehicle_requested vr ON rr.ID_request = vr.ID_request
              JOIN Vehicle v ON vr.ID_vehicle = v.ID_vehicle
              WHERE rr.ID_request = #{requestId}
            
            """)
    BigDecimal getTotalInvestment(@Param("requestId") Long requestId);
}
