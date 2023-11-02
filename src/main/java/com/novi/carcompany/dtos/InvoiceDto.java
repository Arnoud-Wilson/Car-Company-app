package com.novi.carcompany.dtos;

import java.util.Objects;

public class InvoiceDto {

    public Long invoiceNumber;
    public Double totalPrice;
    public Boolean approved;
    public Boolean paid;
    public Double laborHours;
    public EmployeeDto employeeDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceDto that)) return false;
        return Objects.equals(invoiceNumber, that.invoiceNumber) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(approved, that.approved) && Objects.equals(paid, that.paid) && Objects.equals(laborHours, that.laborHours) && Objects.equals(employeeDto, that.employeeDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, totalPrice, approved, paid, laborHours, employeeDto);
    }
}
