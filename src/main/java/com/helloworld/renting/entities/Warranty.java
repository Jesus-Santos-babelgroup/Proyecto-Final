package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "warranty")
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_warranty")
    private Long id;

    @Column(name = "Warranty_type", nullable = false)
    private WarrantyType warrantyType;

    @Column(name = "Warranty_import", nullable = false)
    private BigDecimal warrantyImport;

    @Column(name = "NIF", nullable = false)
    private String nif;

    @Column(name = "Description")
    private String description;
}
