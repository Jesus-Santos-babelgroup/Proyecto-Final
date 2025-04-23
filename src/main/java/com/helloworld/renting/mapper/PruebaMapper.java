package com.helloworld.renting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PruebaMapper {
    @Select("SELECT 'Hola, MyBatis desde MariaDB' AS mensaje")
    String getPrueba();
}