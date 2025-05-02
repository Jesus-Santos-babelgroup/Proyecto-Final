package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountryDto {

    @NotBlank
    private String id;

    @NotBlank
    private String isoA2;
}
