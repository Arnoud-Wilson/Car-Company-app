package com.novi.carcompany.dtos;

import jakarta.validation.constraints.NotBlank;

public class PartInputDto {

    @NotBlank
    public String partNumber;
    @NotBlank
    public String name;
    public String description;
    public String location;
    @NotBlank
    public int quantity;
    @NotBlank
    public Double purchasePrice;
    public Double sellingPrice;

}
