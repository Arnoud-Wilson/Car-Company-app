package com.novi.carcompany.dtos;


import java.util.Objects;

public class CarDto {

    public String licensePlate;
    public String brand;
    public String model;
    public String vinNumber;
    public String color;
    public String engine;
    public Boolean winterTyres;
    public CustomerDto customer;


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof CarDto carDto)) return false;
        return Objects.equals(licensePlate, carDto.licensePlate) && Objects.equals(brand, carDto.brand) && Objects.equals(model, carDto.model) && Objects.equals(vinNumber, carDto.vinNumber) && Objects.equals(color, carDto.color) && Objects.equals(engine, carDto.engine) && Objects.equals(winterTyres, carDto.winterTyres) && Objects.equals(customer, carDto.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, brand, model, vinNumber, color, engine, winterTyres, customer);
    }
}

