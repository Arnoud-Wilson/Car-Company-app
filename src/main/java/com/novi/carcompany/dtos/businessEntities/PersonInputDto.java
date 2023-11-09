package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public abstract class PersonInputDto {

    @NotBlank
    public String surName;
    @NotBlank
    public String lastName;
    public String address;
    @Size(min = 10, max = 20)
    public String phoneNumber;
}
