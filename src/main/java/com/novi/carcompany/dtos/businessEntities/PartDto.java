package com.novi.carcompany.dtos.businessEntities;


import com.novi.carcompany.models.businessEntities.FileDocument;

import java.util.Objects;

public class PartDto {

    public String partNumber;
    public String name;
    public String description;
    public String location;
    public int stock;
    public Double purchasePrice;
    public Double sellingPrice;
    public FileDocument picknote;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartDto partDto)) return false;
        return stock == partDto.stock && Objects.equals(partNumber, partDto.partNumber) && Objects.equals(name, partDto.name) && Objects.equals(description, partDto.description) && Objects.equals(location, partDto.location) && Objects.equals(purchasePrice, partDto.purchasePrice) && Objects.equals(sellingPrice, partDto.sellingPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, name, description, location, stock, purchasePrice, sellingPrice);
    }
}
