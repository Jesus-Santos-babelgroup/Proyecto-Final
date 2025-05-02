package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CountryMapper {


    @Select("SELECT COUNT(ID_country) > 0 FROM Country WHERE iso_a2 = #{isoa2}")
    boolean existsByISOA2(String isoa2);

    @Select("SELECT ID_country FROM Country WHERE iso_a2 = #{isoa2}")
    @Results({
            @Result(property = "id", column = "ID_country"),
    })
    String IDByISOA2(String isoa2);

    @Select("SELECT ID_country, Name, iso_a2, iso_a3, iso_n3 FROM Country WHERE ID_country = #{id}")
    @Results({
            @Result(property = "id", column = "ID_country"),
            @Result(property = "name", column = "Name"),
            @Result(property = "isoA2", column = "iso_a2"),
            @Result(property = "isoA3", column = "iso_a3"),
            @Result(property = "isoN3", column = "iso_n3")
    })
    Country getCountry(String id);
}