package com.helloworld.renting.dto;

public class ClientDto {
    private Long id;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String phone;
    private String nif;
    private String dateOfBirth;
    private String email;
    private Integer scoring;
    private Long addressId;
    private Long countryId;
    private Long notificationAddressId;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFirstSurname() { return firstSurname; }
    public void setFirstSurname(String firstSurname) { this.firstSurname = firstSurname; }

    public String getSecondSurname() { return secondSurname; }
    public void setSecondSurname(String secondSurname) { this.secondSurname = secondSurname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getScoring() { return scoring; }
    public void setScoring(Integer scoring) { this.scoring = scoring; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }

    public Long getNotificationAddressId() { return notificationAddressId; }
    public void setNotificationAddressId(Long notificationAddressId) { this.notificationAddressId = notificationAddressId; }
}
