package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountryDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String isoA2;

    @NotBlank
    private String isoA3;

    @NotBlank
    private String isoN3;

}
