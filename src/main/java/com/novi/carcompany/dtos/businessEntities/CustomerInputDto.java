package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotNull;


public class CustomerInputDto extends PersonInputDto {

    public String bankAccount;
    @NotNull
    public Boolean corporate;

}
