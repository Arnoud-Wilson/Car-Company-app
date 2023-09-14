package com.novi.carcompany.models;

import jakarta.persistence.*;


@Entity
@Table(name = "cars")
public class Car {

    @Id
    @Column(name = "licensePlate", nullable = false, updatable = false)
    private String licensePlate;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "vinNumber")
    private String vinNumber;
    @Column(name = "color")
    private String color;
    @Column(name = "engine")
    private String engine;
    @Column(name = "winterTyres")
    private Boolean winterTyres;
    //TODO: add customer? (foreign key)


    public Car() {
    }

    public Car(String licensePlate, String brand, String model, String vinNumber, String color, String engine) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.vinNumber = vinNumber;
        this.color = color;
        this.engine = engine;
    }

    public Car(String licensePlate, String brand, String model, String vinNumber, String color, String engine, Boolean winterTyres) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.vinNumber = vinNumber;
        this.color = color;
        this.engine = engine;
        this.winterTyres = winterTyres;
    }


    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVinNumber() {
        return this.vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Boolean getWinterTyres() {
        return this.winterTyres;
    }

    public void setWinterTyres(Boolean winterTyres) {
        this.winterTyres = winterTyres;
    }
}
