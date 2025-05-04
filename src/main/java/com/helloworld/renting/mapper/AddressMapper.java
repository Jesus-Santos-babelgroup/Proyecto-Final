package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Address;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT ID_address FROM Address WHERE City = #{city} AND Zip_code = #{zipCode} AND (#{street} IS NULL OR Street = #{street})")
    Long findAddressByDetails(String city, String zipCode, String street);

    @Insert("INSERT INTO Address (City, Zip_code, Street) VALUES (#{city}, #{zipCode}, #{street})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_address")
    int insertAddress(Address address);

    @Select("SELECT ID_address FROM Address WHERE City = #{city} AND " +
            "(#{zipCode} IS NULL OR Zip_code = #{zipCode}) AND " +
            "(#{street} IS NULL OR Street = #{street}) " +
            "LIMIT 1")
    @Results({
            @Result(property = "id", column = "ID_address"),
    })
    Long findAddressIdByCityAndDetails(String city, String zipCode, String street);
}