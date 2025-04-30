package com.helloworld.renting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AddressMapper {

    @Select("SELECT COUNT(*) > 0 FROM Address WHERE ID_address = #{idAddress}")
    boolean existsByAddressId(Long idAddress);
}