package com.novi.carcompany.models;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "cars")
public class Car {

    @Id
    @Column(updatable = false)
    private String licensePlate;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    private String vinNumber;
    private String color;
    private String engine;
    private Boolean winterTyres;

    @ManyToOne
    private Customer customer;


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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return Objects.equals(licensePlate, car.licensePlate) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(vinNumber, car.vinNumber) && Objects.equals(color, car.color) && Objects.equals(engine, car.engine) && Objects.equals(winterTyres, car.winterTyres) && Objects.equals(customer, car.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, brand, model, vinNumber, color, engine, winterTyres, customer);
    }
}
