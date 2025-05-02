package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClientMapper {

    @Insert("INSERT INTO Client (Name, First_surname, Second_surname, ID_address, ID_country, Phone, NIF, Date_of_birth, Email, Scoring, ID_notification_address) " +
            "VALUES (#{name}, #{firstSurname}, #{secondSurname}, #{addressId}, #{countryId}, #{phone}, #{nif}, #{dateOfBirth}, #{email}, #{scoring}, #{notificationAddressId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_client")
    int insert(Client client);

    @Select("SELECT COUNT(*) FROM Client WHERE NIF = #{nif}")
    boolean existsByNif(String nif);

    @Select("SELECT COUNT(*) FROM Client WHERE Email = #{email}")
    boolean existsByEmail(String email);

    @Select("SELECT COUNT(*) FROM Client WHERE Phone = #{phone}")
    boolean existsByPhone(String phone);

    @Select("SELECT COUNT(*) FROM Client WHERE ID_client = #{id}")
    boolean existsById(Long id);

    @Delete("DELETE FROM Client WHERE ID_client=#{id}")
    boolean deleteById(Long id);
}