package com.novi.carcompany.dtos.businessEntities;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarInputDto that)) return false;
        return Objects.equals(licensePlate, that.licensePlate) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(vinNumber, that.vinNumber) && Objects.equals(color, that.color) && Objects.equals(engine, that.engine) && Objects.equals(winterTyres, that.winterTyres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, brand, model, vinNumber, color, engine, winterTyres);
    }
}
