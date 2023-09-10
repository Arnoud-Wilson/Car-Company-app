package com.novi.carcompany.models;


import jakarta.persistence.*;

@Entity
@Table(name = "empoyes")
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "function")
    private String function;

    public Employee() {
    }

    public Employee(String surName, String lastName, String address, int phoneNumber, String function) {
        super(surName, lastName, address, phoneNumber);
        this.function = function;
    }

    public Long getId() {
        return this.id;
    }

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
