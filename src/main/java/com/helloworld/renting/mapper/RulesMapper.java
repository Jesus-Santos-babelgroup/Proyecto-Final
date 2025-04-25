package com.helloworld.renting.mapper;

import com.helloworld.renting.dto.DebtDto;
import com.helloworld.renting.dto.InformaDto;
import com.helloworld.renting.dto.NonpaymentDto;
import com.helloworld.renting.dto.RulesContextDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RulesMapper {

    @Select("""
              SELECT
                r.ID_request,
                r.Investment           AS totalInvestment,
                r.Months_Hired         AS monthsHired,
                r.Contracting_date     AS contractingDate,
                r.Start_date           AS startDate,
                c.NIF                  AS clientNif,
                c.Date_of_birth        AS clientBirthDate,
                c.Nationality          AS clientNationality,
                c.Scoring              AS clientScoring,
                e.Start_date           AS employmentStartDate,
                e.Net_income           AS netIncomeEmployed,
                s.Gross_income         AS grossIncomeSelfEmployed,
                s.Net_income           AS netIncomeSelfEmployed
              FROM renting_request r
              JOIN client c ON c.ID_client = r.ID_client
              LEFT JOIN economic_data_employed e ON e.ID_client = c.ID_client
              LEFT JOIN economic_data_self_employed s ON s.ID_client = c.ID_client
              WHERE r.ID_request = #{requestId}
            """)

    @Results(id = "RulesContextMap", value = {
            @Result(property = "requestId", column = "ID_request"),
            @Result(property = "totalInvestment", column = "totalInvestment"),
            @Result(property = "monthsHired", column = "monthsHired"),
            @Result(property = "contractingDate", column = "contractingDate"),
            @Result(property = "startDate", column = "startDate"),
            @Result(property = "clientNif", column = "clientNif"),
            @Result(property = "clientBirthDate", column = "clientBirthDate"),
            @Result(property = "clientNationality", column = "clientNationality"),
            @Result(property = "clientScoring", column = "clientScoring"),
            @Result(property = "employmentStartDate", column = "employmentStartDate"),
            @Result(property = "netIncomeEmployed", column = "netIncomeEmployed"),
            @Result(property = "grossIncomeSelfEmployed", column = "grossIncomeSelfEmployed"),
            @Result(property = "netIncomeSelfEmployed", column = "netIncomeSelfEmployed"),

            // colecciones anidadas
            @Result(property = "debts", column = "clientNif",
                    many = @Many(select = "selectDebtsByClientNif")),
            @Result(property = "nonpayments", column = "requestId",
                    many = @Many(select = "selectNonpaymentsByClientId")),
            @Result(property = "informaRecords", column = "clientNif",
                    many = @Many(select = "selectInformaByCif")),
            // flags
            @Result(property = "previouslyRejected",
                    column = "requestId",
                    one = @One(select = "wasPreviouslyRejected")),
            @Result(property = "previouslyApprovedWithGuarantee",
                    column = "requestId",
                    one = @One(select = "wasPreviouslyApprovedWithGuarantee"))
    })
    RulesContextDto getRulesContext(@Param("requestId") Long requestId);

    @Select("SELECT ID_debt AS id, NIF AS nif, Category_company AS categoryCompany, Amount AS amount " +
            "FROM debt " +
            "WHERE NIF = #{nif}")
    List<DebtDto> selectDebtsByClientNif(@Param("nif") String nif);

    @Select("SELECT ID_nonpayment AS id, ID_client AS idClient, Category AS category, Start_year AS startYear, Payment_amount AS paymentAmount, Initial_total_import AS initialTotalImport " +
            "FROM nonpayment " +
            "WHERE ID_client = #{requestId}")
    List<NonpaymentDto> selectNonpaymentsByClientId(@Param("requestId") Long requestId);

    @Select("SELECT ID_informa_registry AS id, CIF AS cif, Company_name AS companyName, Municipality AS municipality, Zip_code AS zipCode, Amount_sales AS amountSales, Profit_before_tax AS profitBeforeTax, Fiscal_year AS fiscalYear " +
            "FROM informa_registry " +
            "WHERE CIF = #{nif} " +
            "ORDER BY Fiscal_year DESC LIMIT 3")
    List<InformaDto> selectInformaByCif(@Param("nif") String nif);

    @Select("""
              SELECT CASE WHEN COUNT(*)>0 THEN TRUE ELSE FALSE END
              FROM renting_request
              WHERE ID_client = (SELECT ID_client FROM renting_request WHERE ID_request = #{requestId})
                 AND Final_result = 'DENIED'
                 AND Contracting_date > DATE_SUB(CURDATE(), INTERVAL 2 YEAR)
            """)
    boolean wasPreviouslyRejected(@Param("requestId") Long requestId);

    @Select("""
              SELECT CASE WHEN COUNT(*)>0 THEN TRUE ELSE FALSE END
              FROM renting_request
              WHERE ID_client = (SELECT ID_client FROM renting_request WHERE ID_request = #{requestId})
                 AND Final_result = 'APPROVED_WITH_GUARANTEE'
                 AND Contracting_date > DATE_SUB(CURDATE(), INTERVAL 2 YEAR)
            """)
    boolean wasPreviouslyApprovedWithGuarantee(@Param("requestId") Long requestId);
}