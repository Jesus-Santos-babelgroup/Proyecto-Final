package com.helloworld.renting.service.request.approval.rules.approved.notGuarantorIfNewClient;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotGuarantorIfNewClientMapper {
    @Select("""
    SELECT NOT EXISTS (
        SELECT 1
        FROM renting_request
        WHERE ID_client = (
            SELECT ID_client FROM renting_request WHERE ID_request = #{requestId}
        )
        AND Final_result IN ('APPROVED', 'APPROVED_WITH_GUARANTEE')
    )
""")
    boolean isNewClientByRequestId(@Param("requestId") Long requestId);

    @Select("""
    SELECT EXISTS (
        SELECT 1
        FROM renting_request r
        JOIN warranty w ON r.ID_warranty = w.ID_warranty
        WHERE w.NIF_guarantor = #{nif}
          AND r.Final_result = 'APPROVED_WITH_GUARANTEE'
    )
""")
    boolean hasBeenGuarantorInApprovedWithGuarantee(@Param("nif") String nif);
}
