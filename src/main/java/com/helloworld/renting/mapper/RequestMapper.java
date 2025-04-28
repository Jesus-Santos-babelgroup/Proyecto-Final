package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.RentingRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RequestMapper {

    @Insert("INSERT INTO RentingRequest (clientId, warrantyId, quotaFinal, quotaBaseMonthlyFee, contractingDate, startDate, preResultType, finalResultType, monthsHired) " +
            "VALUES (#{clientId}, #{warrantyId}, #{quotaFinal}, #{quotaBaseMonthlyFee}, #{contractingDate}, #{startDate}, #{preResultType}, #{finalResultType}, #{monthsHired})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(RentingRequest rentingRequest);

    @Select("SELECT * FROM RentingRequest WHERE id = #{id}")
    RentingRequest findById(Long id);

    @Select("SELECT * FROM RentingRequest")
    List<RentingRequest> getAll();
}
