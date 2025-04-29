package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientMapper {

    @Insert("INSERT INTO Client (Name, First_surname, Second_surname, ID_address, ID_country, Phone, NIF, Date_of_birth, Email, Scoring, ID_notification_address) " +
            "VALUES (#{name}, #{firstSurname}, #{secondSurname}, #{addressId}, #{countryId}, #{phone}, #{nif}, #{dateOfBirth}, #{email}, #{scoring}, #{notificationAddressId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_client")
    int insert(Client client);

    @Select("SELECT id_client, name, first_surname, second_surname, email, nif, phone, " +
            "date_of_birth, scoring, id_country, id_address, id_notification_address " +
            "FROM Client " +
            "WHERE id = #{id}")
    Client findById(Long id);

    @Select("SELECT COUNT(*) FROM Client WHERE NIF = #{nif}")
    boolean existsByNif(String nif);

    @Select("SELECT COUNT(*) FROM Client WHERE Email = #{email}")
    boolean existsByEmail(String email);
}