package com.helloworld.renting.mapper.economicalData;

import com.helloworld.renting.entities.EconomicDataEmployed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EconomicalDataEmployedMapper {
    @Select("""
        SELECT ID_employee_data AS id,
               ID_client AS clientId,
               CIF AS cif,
               Gross_income AS grossIncome,
               Net_income AS netIncome,
               Start_date AS startDate,
               End_date AS endDate,
               Year_entry AS yearEntry
        FROM Economic_data_employed
        WHERE ID_employee_data = #{id}
    """)
    EconomicDataEmployed findById(@Param("id") Long id);

    @Update("""
        UPDATE Economic_data_employed
        SET CIF = #{cif},
            Gross_income = #{grossIncome},
            Net_income = #{netIncome},
            Start_date = #{startDate},
            End_date = #{endDate},
            Year_entry = #{yearEntry}
        WHERE ID_employee_data = #{id}
    """)
    int update(EconomicDataEmployed economicDataEmployed);
}
