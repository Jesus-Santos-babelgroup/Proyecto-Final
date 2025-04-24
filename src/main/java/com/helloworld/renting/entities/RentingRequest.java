package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "renting_request")
public class RentingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_request")
    private Long id;

    @Column(name = "ID_client", nullable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_client", referencedColumnName = "ID_client", insertable = false, updatable = false)
    private Client client;

    @Column(name = "Warranty_id")
    private Long warrantyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Warranty_id", referencedColumnName = "ID_warranty", insertable = false, updatable = false)
    private Warranty warranty;

    @Column(name = "Quota_final", nullable = false)
    private BigDecimal quotaFinal;

    @Column(name = "Quota_base_monthly_fee", nullable = false)
    private BigDecimal quotaBaseMonthlyFee;

    @Column(name = "Contracting_date", nullable = false)
    private LocalDate contractingDate;

    @Column(name = "Start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "PreResult", nullable = false)
    private PreResultType preResultType;

    @Column(name = "Final_result")
    private FinalResultType finalResultType;

}
