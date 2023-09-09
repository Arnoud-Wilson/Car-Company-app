package com.novi.carcompany.models;


import jakarta.persistence.*;

@Entity
public class Person {

    //TODO: inheritence...? how?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "surName", nullable = false, updatable = false)
    private String surName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "adress")
    private String adress;
    @Column(name = "phoneNumber")
    private int phoneNumber;


}
