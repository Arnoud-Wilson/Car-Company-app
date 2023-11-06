package com.novi.carcompany.models.businessEntities;

import jakarta.persistence.*;


@MappedSuperclass
public abstract class Person {

    @Column(nullable = false, updatable = false)
    private String surName;
    @Column(nullable = false)
    private String lastName;
    private String address;
    private String phoneNumber;


    public Person() {
    }

    public Person(String surName, String lastName, String address, String phoneNumber) {
        this.surName = surName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
