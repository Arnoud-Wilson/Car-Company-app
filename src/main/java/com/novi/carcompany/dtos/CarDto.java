package com.novi.carcompany.dtos;

import com.novi.carcompany.models.Car;
import jakarta.validation.constraints.NotBlank;

public class CarDto {

    @NotBlank
    private String licensePlate;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    private String vinNumber;
    private String color;
    private String engine;
    private Boolean winterTyres;


    public static CarDto fillCarDto(Car car) {

        CarDto dto = new CarDto();

        dto.licensePlate = car.getLicencePlate();
        dto.brand = car.getBrand();
        dto.model = car.getModel();
        dto.vinNumber = car.getVinNumber();
        dto.color = car.getColor();
        dto.engine = car.getEngine();
        dto.winterTyres = car.getWinterTyres();

        return dto;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Boolean getWinterTyres() {
        return winterTyres;
    }

    public void setWinterTyres(Boolean winterTyres) {
        this.winterTyres = winterTyres;
    }
}
