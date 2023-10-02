package com.novi.carcompany.models;


import jakarta.persistence.*;

@MappedSuperclass
public abstract class Person {

    @Column(name = "surName", nullable = false, updatable = false)
    private String surName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "phoneNumber")
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
