package com.helloworld.renting.repository;

import com.helloworld.renting.entities.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClientRepository {

    @Select("SELECT * FROM client WHERE id = #{id}")
    Client findById(Long id);

    @Select("SELECT * FROM client WHERE cif = #{cif}")
    Client findByCif(String cif);

    @Select("SELECT * FROM client")
    List<Client> findAll();

    @Insert("INSERT INTO client (cif, name, birth_date, net_income_salaried, net_income_self, nationality, rating, internal_blacklist, debt, employment_seniority) " +
            "VALUES (#{cif}, #{name}, #{birthDate}, #{netIncomeSalaried}, #{netIncomeSelf}, #{nationality}, #{rating}, #{internalBlacklist}, #{debt}, #{employmentSeniority})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Client client);

    @Update("UPDATE client SET cif=#{cif}, name=#{name}, birth_date=#{birthDate}, net_income_salaried=#{netIncomeSalaried}, net_income_self=#{netIncomeSelf}, nationality=#{nationality}, rating=#{rating}, internal_blacklist=#{internalBlacklist}, debt=#{debt}, employment_seniority=#{employmentSeniority} WHERE id=#{id}")
    void update(Client client);

    @Delete("DELETE FROM client WHERE id = #{id}")
    void delete(Long id);
}
