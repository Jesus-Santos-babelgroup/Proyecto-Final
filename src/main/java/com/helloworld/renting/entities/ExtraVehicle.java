package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "extra_vehicle")
@Getter
@Setter
public class ExtraVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_extra_vehicle")
    private Long id;

    @Column(name = "Description")
    private String description;

    @Column(name = "Quota_increment")
    private BigDecimal quotaIncrement;

    @Column(name = "Increment_type")
    private IncrementType incrementType;
}
