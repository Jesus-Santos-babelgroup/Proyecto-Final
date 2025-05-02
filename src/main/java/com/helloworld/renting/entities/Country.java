package com.helloworld.renting.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Country {

    @NotBlank
    private String id;

    @NotBlank
    private String name;
}
