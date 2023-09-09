package com.novi.carcompany.models;

import jakarta.persistence.*;

@Entity
@Table(name = "customerd")
public class Customer extends Person {

    //TODO: set inheritance? annotation?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "bankAccount")
    private String bankAccount;
    @Column(name = "corporate")
    private Boolean corporate;
    //TODO: add Car (foreign key)


    public Customer() {
    }

    public Customer(String bankAccount, Boolean corporate) {
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
}
