package com.helloworld.renting.mapper;


import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ClientMapper {
    @Update("""
                UPDATE Client
                SET Name = #{client.name},
                    First_surname = #{client.firstSurname},
                    Second_surname = #{client.secondSurname},
                    Email = #{client.email},
                    Nif = #{client.nif},
                    Phone = #{client.phone},
                    Date_of_birth = #{client.dateOfBirth},
                    Scoring = #{client.scoring},
                    Country_id = #{client.countryId},
                    Address_id = #{client.addressId},
                    Notification_address_id = #{client.notificationAddressId}
                WHERE ID_client = #{client.id}
            """)
    Client updateClient(Client client);
}
