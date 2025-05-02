package com.helloworld.renting.service.request.approval.rules.approved.norecentapprovalwithguarantee;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface NoRecentApprovalWithGuaranteeMapper {

    @Select("""
        SELECT COUNT(ID_request) 
        FROM Renting_request
        WHERE ID_client = #{clientId}
          AND Final_result = 'APPROVED_WITH_WARRANTY'
          AND Contracting_date >= #{fromDate}
        """)
    int countPreviousApprovalsWithGuarantees(@Param("clientId") Long clientId, @Param("fromDate") LocalDate fromDate);
}
