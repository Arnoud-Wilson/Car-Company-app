package com.novi.carcompany.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CarInputDto {

    @NotBlank
    public String licensePlate;
    @NotBlank
    public String brand;
    @NotBlank
    public String model;

    public String vinNumber;
    public String color;
    public String engine;
    public Boolean winterTyres;

}
