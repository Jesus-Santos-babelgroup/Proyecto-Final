package com.helloworld.renting.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class ClientDto {

    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String firstSurname;

    private String secondSurname;

    @Email
    private String email;

    @NotBlank
    private String nif;

    private String phone;

    private LocalDate dateOfBirth;

    private Integer scoring;

    @NotBlank
    private String countryId;

    @NotNull
    @Positive
    private Long addressId;

    @Positive
    private Long notificationAddressId;
}
