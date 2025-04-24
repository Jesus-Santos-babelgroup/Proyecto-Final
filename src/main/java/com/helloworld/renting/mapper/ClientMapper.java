package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClientMapper {

    @Insert("INSERT INTO clients (name, first_surname, second_surname, address_id, country_id, phone, nif, date_of_birth, email, scoring) " +
            "VALUES (#{name}, #{firstSurname}, #{secondSurname}, #{addressId}, #{countryId}, #{phone}, #{nif}, #{dateOfBirth}, #{email}, #{scoring})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Client client);

    @Select("SELECT COUNT(*) FROM clients WHERE nif = #{nif}")
    boolean existsByNif(String nif);

    @Select("SELECT COUNT(*) FROM clients WHERE email = #{email}")
    boolean existsByEmail(String email);
}