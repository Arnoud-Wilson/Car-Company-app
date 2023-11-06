package com.novi.carcompany.dtos;

import java.util.Objects;


public class PartChangeInputDto {

    public String partNumber;
    public String name;
    public String description;
    public String location;
    public int quantity;
    public Double purchasePrice;
    public Double sellingPrice;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartChangeInputDto that)) return false;
        return quantity == that.quantity && Objects.equals(partNumber, that.partNumber) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(location, that.location) && Objects.equals(purchasePrice, that.purchasePrice) && Objects.equals(sellingPrice, that.sellingPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, name, description, location, quantity, purchasePrice, sellingPrice);
    }
}
