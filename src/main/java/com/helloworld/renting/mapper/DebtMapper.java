package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Debt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DebtMapper {
    @Select("SELECT * FROM Debt WHERE nif = #{nif}")
    List<Debt> findDebtsByNif(String nif);
}
