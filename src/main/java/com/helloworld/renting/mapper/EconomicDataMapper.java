package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EconomicDataMapper {

    @Select("SELECT ede.ID_employee_data, ede.ID_client, ede.CIF, ede.Gross_income, ede.Start_date, ede.End_date, ede.Year_entry, ede.Net_income " +
            "FROM Economic_data_employed ede WHERE ede.ID_client = #{clientId}")
    @Results({
            @Result(property = "id", column = "ID_employee_data"),
            @Result(property = "clientId", column = "ID_client"),
            @Result(property = "cif", column = "CIF"),
            @Result(property = "grossIncome", column = "Gross_income"),
            @Result(property = "startDate", column = "Start_date"),
            @Result(property = "endDate", column = "End_date"),
            @Result(property = "yearEntry", column = "Year_entry"),
            @Result(property = "netIncome", column = "Net_income")})
    List<EconomicDataEmployed> getEconomicDataEmployedByClientId(Long clientId);

    @Select("SELECT ede.ID_autonomous_data, ede.ID_client, ede.Gross_income, ede.Net_income, ede.Year_entry " +
            "FROM Economic_data_self_employed ede WHERE ede.ID_client = #{clientId}")
    @Results({
            @Result(property = "id", column = "ID_employee_data"),
            @Result(property = "clientId", column = "ID_client"),
            @Result(property = "grossIncome", column = "Gross_income"),
            @Result(property = "netIncome", column = "Net_income"),
            @Result(property = "yearEntry", column = "Year_entry")})
    List<EconomicDataSelfEmployed> getEconomicDataSelfEmployedByClientId(Long clientId);

    @Delete("DELETE FROM Economic_data_employed WHERE ID_client = #{clientId}")
    void deleteEconomicDataEmployedByClientId(Long clientId);

    @Delete("DELETE FROM Economic_data_self_employed WHERE ID_client = #{clientId}")
    void deleteEconomicDataSelfEmployedByClientId(Long clientId);

}
