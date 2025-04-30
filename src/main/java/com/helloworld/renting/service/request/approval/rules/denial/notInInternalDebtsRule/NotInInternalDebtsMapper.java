package com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule;

import com.helloworld.renting.dto.NonpaymentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotInInternalDebtsMapper {

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
    List<NonpaymentDto> findByClientId(@Param("clientId") Long clientId);

}
