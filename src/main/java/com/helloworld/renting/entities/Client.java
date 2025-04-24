package com.helloworld.renting.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_client")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "First_surname", nullable = false)
    private String firstSurname;

    @Column(name = "Second_surname")
    private String secondSurname;

    @Column(name = "Email")
    private String email;

    @Column(name = "NIF", nullable = false, unique = true)
    private String nif;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Date_of_birth")
    private java.time.LocalDate dateOfBirth;

    @Column(name = "Scoring")
    private Integer scoring;

    @Column(name = "ID_country", nullable = false)
    private String countryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_country", referencedColumnName = "ID_country", insertable = false, updatable = false)
    private Country country;

    @Column(name = "ID_address", nullable = false)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_address", referencedColumnName = "ID_address", insertable = false, updatable = false)
    private Address address;

    @Column(name = "ID_notification_address")
    private Long notificationAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_notification_address", referencedColumnName = "ID_address", insertable = false, updatable = false)
    private Address notificationAddress;
}
