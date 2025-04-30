package com.helloworld.renting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CountryMapper {

    @Select("SELECT COUNT(*) > 0 FROM Country WHERE ID_country = #{idCountry}")
    boolean existsByCountryId(Long idCountry);
}