package com.novi.carcompany.dtos;

import java.util.Objects;

public class CustomerDto extends PersonDto {

    public Long id;
    public String bankAccount;
    public Boolean corporate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(bankAccount, that.bankAccount) && Objects.equals(corporate, that.corporate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccount, corporate);
    }
}
