package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "extra_requested")
public class ExtraRequested {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_extra_requested")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_vehicle_requested", referencedColumnName = "ID_vehicle_requested")
    private VehicleRequested vehicleRequested;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_extra_vehicle", referencedColumnName = "ID_extra_vehicle")
    private ExtraVehicle extraVehicle;

}
