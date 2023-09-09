package com.novi.carcompany.models;

import jakarta.persistence.*;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceNumber", nullable = false, updatable = false)
    private Long invoiceNumber;
    @Column(name = "totalPrice", nullable = false)
    private Double totaalPrice;
    @Column(name = "approved", nullable = false)
    private Boolean approved;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "labor")
    private String name;
    //TODO: add Part, Customer, Car (foreign key)


    public Invoice() {
    }

    public Invoice(Long invoiceNumber, Double totaalPrice, Boolean approved, Boolean paid, String name) {
        this.invoiceNumber = invoiceNumber;
        this.totaalPrice = totaalPrice;
        this.approved = approved;
        this.paid = paid;
        this.name = name;
    }


    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getTotaalPrice() {
        return this.totaalPrice;
    }

    public void setTotaalPrice(Double totaalPrice) {
        this.totaalPrice = totaalPrice;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
