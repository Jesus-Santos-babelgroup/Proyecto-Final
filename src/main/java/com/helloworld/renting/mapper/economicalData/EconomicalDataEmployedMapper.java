package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface EconomicalDataEmployedMapper {

    @Insert("INSERT INTO Economic_data_employed (ID_client, CIF, Gross_income, Net_income, Start_date, End_date, Year_entry) " +
            "VALUES (#{clientId}, #{cif}, #{grossIncome}, #{netIncome}, #{startDate}, #{endDate}, #{yearEntry})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_employed_data")
    int insert(EconomicDataEmployed economicDataEmployed);

}
