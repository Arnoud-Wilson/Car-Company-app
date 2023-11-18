package com.novi.carcompany.dtos.businessEntities;


import java.util.Objects;

public class InvoiceInputDto {

    public Double totalPrice;
    public Boolean approved;
    public Boolean paid;
    public Double laborHours;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceInputDto that)) return false;
        return Objects.equals(totalPrice, that.totalPrice) && Objects.equals(approved, that.approved) && Objects.equals(paid, that.paid) && Objects.equals(laborHours, that.laborHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice, approved, paid, laborHours);
    }
}
