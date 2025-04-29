package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import com.helloworld.renting.entities.EconomicDataSelfEmployed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface EconomicalDataSelfEmployedMapper {

    @Insert("INSERT INTO Economic_data_self_employed (ID_client, Gross_income, Net_income, Year_entry) " +
            "VALUES (#{clientId}, #{grossIncome}, #{netIncome}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_autonomous_data")
    int insert(EconomicDataSelfEmployed economicDataSelfEmployed);

}

