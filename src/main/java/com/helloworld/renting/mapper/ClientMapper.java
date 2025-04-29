package com.helloworld.renting.mapper;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT COUNT(*) FROM Client WHERE Email = #{email}")
    boolean existByEmail(String email);

    @Select("SELECT COUNT(*) FROM Client WHERE NIF = #{nif}")
    boolean existByNif(String nif);

    @Select("SELECT COUNT(*) FROM Client WHERE ID_client = #{id}")
    boolean existById(Long id);

    @Select("SELECT COUNT(*) FROM Client WHERE Phone = #{tlf}")
    boolean existByTlf(String tlf);


}