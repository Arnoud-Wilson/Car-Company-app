package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotBlank;


public class EmployeeInputDto extends PersonInputDto {

    @NotBlank
    public String function;

}
