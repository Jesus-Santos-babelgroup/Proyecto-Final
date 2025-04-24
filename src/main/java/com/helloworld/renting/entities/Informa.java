package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "informa_registry")
@Getter
@Setter
public class Informa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_informa_registry")
    private Long id;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "Company_name")
    private String companyName;

    @Column(name = "Municipality")
    private String municipality;

    @Column(name = "Zip_code")
    private String zipCode;

    @Column(name = "Amount_sales")
    private BigDecimal amountSales;

    @Column(name = "Profit_before_tax")
    private BigDecimal profitBeforeTax;

    @Column(name = "Fiscal_year")
    private Integer fiscalYear;
}
