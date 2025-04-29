package com.helloworld.renting.service.request.approval.rules.denial.notInInternalDebtsRule;

import com.helloworld.renting.dto.NonpaymentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotInInternalDebtsMapper {
    @Select("SELECT * FROM nonpayment WHERE ID_client = #{clientId}")
    List<NonpaymentDto> findByClientId(@Param("clientId") Long clientId);
}
