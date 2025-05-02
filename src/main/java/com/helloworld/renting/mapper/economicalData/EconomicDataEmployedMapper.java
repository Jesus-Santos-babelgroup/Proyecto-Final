package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EconomicDataEmployedMapper {

    @Insert("INSERT INTO Economic_data_employed (ID_client, CIF, Gross_income, Net_income, Start_date, End_date, Year_entry) " +
            "VALUES (#{clientId}, #{cif}, #{grossIncome}, #{netIncome}, #{startDate}, #{endDate}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_employed_data")
    int insert(EconomicDataEmployed economicDataEmployed);

    @Select("SELECT COUNT(*) FROM Economic_data_employed WHERE ID_client = #{clientId} AND Year_entry = #{yearEntry}")
    boolean existsEmployedByClientIdAndYear(@Param("clientId") Long clientId, @Param("yearEntry") Integer yearEntry);

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

    @Delete("DELETE FROM Economic_data_employed WHERE ID_client = #{clientId}")
    void deleteEconomicDataEmployedByClientId(Long clientId);

}
