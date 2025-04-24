package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_address")
    private Long id;

    @Column(name = "City", nullable = false)
    private String city;

    @Column(name = "Zip_code", nullable = false)
    private String zipCode;

    @Column(name = "Street")
    private String street;

    @Column(name = "ID_client", nullable = false)
    private Long clientId;
}
