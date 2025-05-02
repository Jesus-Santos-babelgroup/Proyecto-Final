package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClientMapper {
    @Update("""
                UPDATE Client
                SET Name = #{name},
                    First_surname = #{firstSurname},
                    Second_surname = #{secondSurname},
                    Email = #{email},
                    NIF = #{nif},
                    Phone = #{phone},
                    Date_of_birth = #{dateOfBirth},
                    Scoring = #{scoring},
                    ID_country = #{countryId},
                    ID_address = #{addressId},
                    ID_notification_address = #{notificationAddressId}
                WHERE ID_client = #{id}
            """)
    int updateClient(Client client);

    @Insert("INSERT INTO Client (Name, First_surname, Second_surname, ID_address, ID_country, Phone, NIF, Date_of_birth, Email, Scoring, ID_notification_address) " +
            "VALUES (#{name}, #{firstSurname}, #{secondSurname}, #{addressId}, #{countryId}, #{phone}, #{nif}, #{dateOfBirth}, #{email}, #{scoring}, #{notificationAddressId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID_client")
    int insert(Client client);


    @Select("SELECT id_client, name, first_surname, second_surname, email, nif, phone, " +
            "date_of_birth, scoring, id_country, id_address, id_notification_address " +
            "FROM Client " +
            "WHERE id = #{id}")
    Client findById(Long id);

    @Select("SELECT COUNT(ID_client) FROM Client WHERE NIF = #{nif}")
    int existsByNif(String nif);

    @Select("SELECT COUNT(ID_client) FROM Client WHERE Email = #{email}")
    int existsByEmail(String email);

    @Select("SELECT COUNT(ID_client) FROM Client WHERE ID_client = #{id}")
    boolean existsById(Long id);

    @Select("SELECT COUNT(ID_client) FROM Client WHERE Phone = #{phone}")
    int existsByPhone(String phone);

    @Select("SELECT Scoring FROM Client WHERE ID_client = #{id}")
    int recoverScoring(Long id);

    @Delete("DELETE FROM Client WHERE ID_client=#{id}")
    boolean deleteById(Long id);

}