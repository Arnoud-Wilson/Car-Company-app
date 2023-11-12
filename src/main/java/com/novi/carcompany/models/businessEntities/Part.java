package com.novi.carcompany.models.businessEntities;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;


@Entity
@Table(name = "parts")
public class Part {

    @Id
    @Column(updatable = false)
    private String partNumber;
    @Column(nullable = false)
    private String name;
    private String description;
    private String location;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false, updatable = false)
    private Double purchasePrice;
    private Double sellingPrice;

    @OneToOne
    private FileDocument picknote;


    public Part() {
    }

    public Part(String partNumber, String name, String description, String location, int stock, Double purchasePrice, Double sellingPrice) {
        this.partNumber = partNumber;
        this.name = name;
        this.description = description;
        this.location = location;
        this.stock = stock;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }


    public String getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPurchasePrice() {
        return this.purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public FileDocument getPicknote() {
        return picknote;
    }

    public void setPicknote(FileDocument picknote) {
        this.picknote = picknote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part part)) return false;
        return stock == part.stock && Objects.equals(partNumber, part.partNumber) && Objects.equals(name, part.name) && Objects.equals(description, part.description) && Objects.equals(location, part.location) && Objects.equals(purchasePrice, part.purchasePrice) && Objects.equals(sellingPrice, part.sellingPrice) && Objects.equals(picknote, part.picknote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, name, description, location, stock, purchasePrice, sellingPrice, picknote);
    }
}

