package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AddressMapper {

    @Select("SELECT ID_address, City, Zip_code, Street FROM Address WHERE ID_address = #{idAddress}")
    @Results({
            @Result(property = "id", column = "ID_address"),
            @Result(property = "city", column = "City"),
            @Result(property = "zipCode", column = "Zip_code"),
            @Result(property = "street", column = "Street"),
    })
    Address getAddress(Long idAddress);

    @Select("SELECT ID_address FROM Address WHERE City = #{city}")
    @Results({
            @Result(property = "id", column = "ID_address"),
    })
    Long IdByCity(String city);

    @Select("SELECT COUNT(ID_address) > 0 FROM Address WHERE City = #{city}")
    boolean existsByCity(String city);
}