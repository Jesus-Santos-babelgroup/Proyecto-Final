package com.helloworld.renting.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Setter
@Getter
public class Country {
    @Id
    @Column(name = "ID_country")
    private String id;

    @Column(name = "Country")
    private String name;
}
