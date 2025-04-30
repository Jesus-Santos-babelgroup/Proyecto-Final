package com.helloworld.renting.service.request.approval.rules.approved.InformaDataApproval;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface InformaDataApprovalMapper {

    @Select("""
                SELECT profit_before_tax
                FROM Informa
                WHERE CIF = #{cif}
                  AND Fiscal_year >= #{minYear}
            """)
    List<BigDecimal> findProfitLast3YearsByCif(@Param("cif") String cif, @Param("minYear") int minYear);

    @Select("""
                SELECT CIF
                FROM Economic_data_employed
                WHERE ID_client = #{idClient}
            """)
    String getCifByClientID(@Param("idClient") long idClient);


}
