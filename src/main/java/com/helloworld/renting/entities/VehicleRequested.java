package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "vehicle_requested")
public class VehicleRequested {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vehicle_requested")
    private Long id;

    @Column(name = "ID_request", nullable = false)
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_request", referencedColumnName = "ID_request", insertable = false, updatable = false)
    private RentingRequest request;

    @Column(name = "ID_vehicle", nullable = false)
    private Long vehicleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_vehicle", referencedColumnName = "ID_vehicle", insertable = false, updatable = false)
    private Vehicle vehicle;

    @Column(name = "License_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "Quota_final_vehicle", nullable = false)
    private BigDecimal quotaFinalVehicle;

    @Column(name = "Months_Hired", nullable = false)
    private Integer monthsHired;
}
