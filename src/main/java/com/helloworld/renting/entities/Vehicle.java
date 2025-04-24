package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vehicle")
    private Long id;

    @Column(name = "Model")
    private String model;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Investment")
    private BigDecimal investment;

    @Column(name = "Quota_base")
    private BigDecimal quotaBase;

    @Column(name = "Cubic_capacity")
    private Integer cubicCapacity;

    @Column(name = "Power")
    private Integer power;

    @Column(name = "Color")
    private String color;

    @Column(name = "Number_seats")
    private Integer numberSeats;
}