package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "nonpayment")
@Setter
@Getter
public class Nonpayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_nonpayment")
    private Long id;

    @Column(name = "ID_client", nullable = false)
    private Long idClient;

    @Column(name = "Category")
    private String category;

    @Column(name = "Start_year")
    private Integer startYear;

    @Column(name = "Payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "Initial_total_import")
    private BigDecimal initialTotalImport;
}