package com.novi.carcompany.dtos.businessEntities;

import java.util.Objects;


public class EmployeeDto extends PersonDto {

    public Long id;
    public String function;
    public String userName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(function, that.function) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, function, userName);
    }
}
