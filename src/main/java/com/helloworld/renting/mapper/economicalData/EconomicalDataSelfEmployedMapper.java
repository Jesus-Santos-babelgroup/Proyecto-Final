package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EconomicalDataSelfEmployedMapper {

    @Insert("INSERT INTO EconomicDataSelfEmployed (ID_client, Gross_income, Net_income, Year_entry) " +
            "VALUES (#{clientId}, #{grossIncome}, #{netIncome}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_autonomous_data")
    int insert(EconomicDataSelfEmployed economicDataSelfEmployed);

    @Update("""
        UPDATE Economic_data_self_employed
        SET Gross_income = #{grossIncome},
            Net_income = #{netIncome},
            Year_entry = #{yearEntry}
        WHERE ID_autonomous_data = #{id}
    """)
    int update(EconomicDataSelfEmployed economicDataSelfEmployed);

    @Select("""
        SELECT ID_autonomous_data AS id,
               ID_client AS clientId,
               Gross_income AS grossIncome,
               Net_income AS netIncome,
               Year_entry AS yearEntry
        FROM Economic_data_self_employed
        WHERE ID_autonomous_data = #{id}
    """)
    EconomicDataSelfEmployed findById(@Param("id") Long id);
}

