package com.novi.carcompany.dtos;

import jakarta.validation.constraints.NotBlank;


public class StringInputDto {

    @NotBlank
    public String id;
}
