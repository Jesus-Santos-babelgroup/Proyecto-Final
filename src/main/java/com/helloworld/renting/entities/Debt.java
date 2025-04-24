package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "debt")
@Setter
@Getter
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_debt")
    private Long id;

    @Column(name = "NIF", nullable = false)
    private String nif;

    @Column(name = "Category_company")
    private String categoryCompany;

    @Column(name = "Amount")
    private BigDecimal amount;
}
