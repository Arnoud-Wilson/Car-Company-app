package com.novi.carcompany.models;


import jakarta.persistence.*;

@Entity
@Table(name = "empoyes")
public class Employee implements Person {

    //TODO: add inheritance? annotation?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "function")
    private String function;


    public Employee() {
    }

    public Employee(String function) {
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
