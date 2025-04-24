package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "economic_data_self_employed")
public class EconomicDataSelfEmployed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_autonomous_data")
    private Long id;

    @Column(name = "ID_client", nullable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_client", referencedColumnName = "ID_client", insertable = false, updatable = false)
    private Client client;

    @Column(name = "Gross_income")
    private BigDecimal grossIncome;

    @Column(name = "Net_income")
    private BigDecimal netIncome;

    @Column(name = "Year_entry")
    private Integer yearEntry;
}
