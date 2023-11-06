package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotBlank;


public class StringInputDto {

    @NotBlank
    public String id;
}
