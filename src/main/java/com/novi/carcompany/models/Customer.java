package com.novi.carcompany.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "customers")
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String bankAccount;
    private Boolean corporate;

    @OneToMany(mappedBy = "customer")
    private List<Car> cars;
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;


    public Customer() {
    }

    public Customer(String surName, String lastName, String address, String phoneNumber, String bankAccount, Boolean corporate) {
        super(surName, lastName, address, phoneNumber);
        this.bankAccount = bankAccount;
        this.corporate = corporate;
    }


    public Long getId() {
        return this.id;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Boolean getCorporate() {
        return this.corporate;
    }

    public void setCorporate(Boolean corporate) {
        this.corporate = corporate;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id) && Objects.equals(bankAccount, customer.bankAccount) && Objects.equals(corporate, customer.corporate) && Objects.equals(cars, customer.cars) && Objects.equals(invoices, customer.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccount, corporate, cars, invoices);
    }
}
