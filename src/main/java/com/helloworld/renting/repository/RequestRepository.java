package com.helloworld.renting.repository;

import com.helloworld.renting.entities.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestRepository {

    @Select("SELECT * FROM request WHERE id = #{id}")
    Request findById(Long id);

    @Select("SELECT * FROM request")
    List<Request> findAll();

    @Insert("INSERT INTO request (client_id, request_date, term, investment, monthly_payment, result) " +
            "VALUES (#{clientId}, #{requestDate}, #{term}, #{investment}, #{monthlyPayment}, #{result})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Request request);

    @Delete("DELETE FROM request WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT COUNT(*) FROM request WHERE client_id = #{clientId}")
    int countByClientId(Long clientId);

    @Select("SELECT * FROM request WHERE client_id = #{clientId}")
    List<Request> findByClientId(Long clientId);

    @Select("SELECT COUNT(*) FROM request WHERE client_id = #{clientId} AND result = 'PENDIENTE'")
    int countPendingByClientId(Long clientId);
}
