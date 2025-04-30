package com.helloworld.renting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RequestMapper {

    @Select("SELECT COUNT(*) FROM Renting_request WHERE ID_client = #{clientId}")
    int countByClientId(@Param("clientId") Long clientId);
}
