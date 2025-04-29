package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EconomicalDataSelfEmployedMapper {

    @Insert("INSERT INTO Economic_data_self_employed (ID_client, Gross_income, Net_income, Year_entry) " +
            "VALUES (#{clientId}, #{grossIncome}, #{netIncome}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_autonomous_data")
    int insert(EconomicDataSelfEmployed economicDataSelfEmployed);

    @Select("SELECT COUNT(*) FROM Economic_data_self_employed WHERE ID_client = #{clientId} AND Year_entry = #{yearEntry}")
    boolean existsSelfEmployedByClientIdAndYear(@Param("clientId") Long clientId, @Param("yearEntry") Integer yearEntry);

}

