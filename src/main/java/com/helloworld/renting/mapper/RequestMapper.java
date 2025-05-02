package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.RentingRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RequestMapper {

    @Select("SELECT id_request, id_client, quota_final, contracting_date, start_date, preresult, final_result FROM Renting_request")
    List<RentingRequest> getAll();

    @Select("SELECT id_request, id_client, quota_final, contracting_date, start_date, preresult, final_result FROM Renting_request WHERE id_request = #{id}")
    RentingRequest findById(Long id);

    @Select("SELECT id_request, id_client, quota_final, contracting_date, start_date, preresult, final_result FROM Renting_request WHERE id_client = #{id}")
    List<RentingRequest> findByClientId(Long id);

    @Insert("INSERT INTO Renting_request (id_client, quota_final, contracting_date, start_date, preresult_type, finalresul_type) " +
            "VALUES (#{clientId}, #{quotaFinal}, #{contractingDate}, #{startDate}, #{preResultType}, #{finalResultType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(RentingRequest rentingRequest);
}
