package com.helloworld.renting.mapper.economicData;

import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EconomicDataSelfEmployedMapper {

    @Insert("INSERT INTO Economic_data_self_employed (ID_client, Gross_income, Net_income, Year_entry) " +
            "VALUES (#{clientId}, #{grossIncome}, #{netIncome}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_autonomous_data")
    int insert(EconomicDataSelfEmployed economicDataSelfEmployed);

    @Select("SELECT COUNT(*) FROM Economic_data_self_employed WHERE ID_client = #{clientId} AND Year_entry = #{yearEntry}")
    boolean existsSelfEmployedByClientIdAndYear(@Param("clientId") Long clientId, @Param("yearEntry") Integer yearEntry);

    @Select("SELECT ede.ID_autonomous_data, ede.ID_client, ede.Gross_income, ede.Net_income, ede.Year_entry " +
            "FROM Economic_data_self_employed ede WHERE ede.ID_client = #{clientId}")
    @Results({
            @Result(property = "id", column = "ID_employee_data"),
            @Result(property = "clientId", column = "ID_client"),
            @Result(property = "grossIncome", column = "Gross_income"),
            @Result(property = "netIncome", column = "Net_income"),
            @Result(property = "yearEntry", column = "Year_entry")})
    List<EconomicDataSelfEmployed> getEconomicDataSelfEmployedByClientId(Long clientId);

    @Delete("DELETE FROM Economic_data_self_employed WHERE ID_client = #{clientId}")
    void deleteEconomicDataSelfEmployedByClientId(Long clientId);

}
