package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class Address {

    @Positive
    private Long id;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    private String street;
}
