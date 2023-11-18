package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;


public class CustomerInputDto extends PersonInputDto {

    public String bankAccount;
    @NotNull
    public Boolean corporate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerInputDto that)) return false;
        return Objects.equals(bankAccount, that.bankAccount) && Objects.equals(corporate, that.corporate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccount, corporate);
    }
}
