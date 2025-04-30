package com.helloworld.renting.service.request.approval.rules.approved.notrejectedinlastyears;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface NotRejectedInLastYearsMapper {

    @Select("""
              SELECT COUNT(ID_request) AS count
                FROM Renting_request
                WHERE ID_client = #{clientId}
                  AND Final_result = 'Rejected'
                  AND Contracting_date >= DATE_SUB(CURDATE(), INTERVAL #{years} YEAR)
            """)
    int countRejectedRequestsInLastYears(@Param("clientId") Long clientId, @Param("years") int years);
}
