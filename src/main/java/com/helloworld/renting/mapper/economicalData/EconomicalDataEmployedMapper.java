package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EconomicalDataEmployedMapper {

    @Insert("INSERT INTO Economic_data_employed (ID_client, CIF, Gross_income, Net_income, Start_date, End_date, Year_entry) " +
            "VALUES (#{clientId}, #{cif}, #{grossIncome}, #{netIncome}, #{startDate}, #{endDate}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_employed_data")
    int insert(EconomicDataEmployed economicDataEmployed);

    @Select("SELECT COUNT(*) FROM Economic_data_employed WHERE ID_client = #{clientId} AND Year_entry = #{yearEntry}")
    boolean existsEmployedByClientIdAndYear(@Param("clientId") Long clientId, @Param("yearEntry") Integer yearEntry);
}
