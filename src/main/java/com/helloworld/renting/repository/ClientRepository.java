package com.helloworld.renting.repository;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClientRepository {

    @Select("SELECT * FROM client WHERE id = #{id}")
    Client findById(Long id);

    @Select("SELECT * FROM client WHERE nif = #{nif}")
    Client findByNif(String nif);

    @Select("SELECT * FROM client")
    List<Client> findAll();

    @Insert("INSERT INTO client (nif, name, email) VALUES (#{nif}, #{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Client client);

    @Delete("DELETE FROM client WHERE id = #{id}")
    void delete(Long id);
}
