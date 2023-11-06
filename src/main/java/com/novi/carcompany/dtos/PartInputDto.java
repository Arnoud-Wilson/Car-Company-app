package com.novi.carcompany.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PartInputDto {

    @NotBlank
    public String partNumber;
    @NotBlank
    public String name;
    public String description;
    public String location;
    @NotNull
    public int stock;
    @NotNull
    public Double purchasePrice;
    public Double sellingPrice;
}
