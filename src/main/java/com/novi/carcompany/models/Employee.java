package com.novi.carcompany.models;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "empoyees")
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "function")
    private String function;

    public Employee() {
    }

    public Employee(String surName, String lastName, String address, String phoneNumber, String function) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(id, employee.id) && Objects.equals(function, employee.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, function);
    }
}
