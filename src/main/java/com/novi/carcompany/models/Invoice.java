package com.novi.carcompany.models;

import jakarta.persistence.*;

import java.util.Objects;

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
    @Column(name = "laborHours")
    private Double laborHours;
    //TODO: add Part, Customer, Car (foreign key)


    public Invoice() {
    }

    public Invoice(Long invoiceNumber, Double totaalPrice, Boolean approved, Boolean paid, Double laborHours) {
        this.invoiceNumber = invoiceNumber;
        this.totaalPrice = totaalPrice;
        this.approved = approved;
        this.paid = paid;
        this.laborHours = laborHours;
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

    public Double getLaborHours() {
        return this.laborHours;
    }

    public void setLaborHours(Double laborHours) {
        this.laborHours = laborHours;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;
        return Objects.equals(invoiceNumber, invoice.invoiceNumber) && Objects.equals(totaalPrice, invoice.totaalPrice) && Objects.equals(approved, invoice.approved) && Objects.equals(paid, invoice.paid) && Objects.equals(laborHours, invoice.laborHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, totaalPrice, approved, paid, laborHours);
    }
}
