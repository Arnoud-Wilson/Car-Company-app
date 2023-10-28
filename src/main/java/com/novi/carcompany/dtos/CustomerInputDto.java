package com.novi.carcompany.dtos;

import jakarta.validation.constraints.NotBlank;

public class CustomerInputDto extends PersonInputDto {

    public String bankAccount;
    @NotBlank
    public Boolean corporate;

}
