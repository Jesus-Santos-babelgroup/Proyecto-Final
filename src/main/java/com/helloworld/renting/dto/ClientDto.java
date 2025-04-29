package com.helloworld.renting.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
