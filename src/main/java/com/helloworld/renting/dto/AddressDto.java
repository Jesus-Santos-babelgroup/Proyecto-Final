package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class AddressDto {

    @Positive
    private Long id;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    private String street;
}
