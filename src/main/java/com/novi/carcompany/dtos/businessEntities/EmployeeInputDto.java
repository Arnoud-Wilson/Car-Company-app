package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


public class EmployeeInputDto extends PersonInputDto {

    @NotBlank
    public String function;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeInputDto that)) return false;
        return Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function);
    }
}
