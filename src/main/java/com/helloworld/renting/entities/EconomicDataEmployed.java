package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "economic_data_employed")
public class EconomicDataEmployed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_employee_data")
    private Long id;

    @Column(name = "ID_client", nullable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_client", referencedColumnName = "ID_client", insertable = false, updatable = false)
    private Client client;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "Gross_income")
    private BigDecimal grossIncome;

    @Column(name = "Start_date")
    private LocalDate startDate;

    @Column(name = "Year_entry")
    private Integer yearEntry;
}
