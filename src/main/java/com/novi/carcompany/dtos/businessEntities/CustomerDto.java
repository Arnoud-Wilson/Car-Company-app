package com.novi.carcompany.dtos.businessEntities;

import java.util.List;
import java.util.Objects;


public class CustomerDto extends PersonDto {

    public Long id;
    public String bankAccount;
    public Boolean corporate;
    public List<String> cars;
    public String userName;


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof CustomerDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(bankAccount, that.bankAccount) && Objects.equals(corporate, that.corporate) && Objects.equals(cars, that.cars) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccount, corporate, cars, userName);
    }
}
