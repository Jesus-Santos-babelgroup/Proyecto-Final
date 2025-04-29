package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
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

    @Select("SELECT COUNT(*) FROM Client WHERE Email = #{email}")
    boolean existsByEmail(String email);

    @Select("SELECT COUNT(*) FROM Client WHERE NIF = #{nif}")
    boolean existsByNif(String nif);

    @Select("SELECT COUNT(*) FROM Client WHERE ID_client = #{id}")
    boolean existsById(Long id);

    @Select("SELECT COUNT(*) FROM Client WHERE Phone = #{tlf}")
    boolean existsByTlf(String tlf);

}