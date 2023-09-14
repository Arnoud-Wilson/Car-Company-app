package com.novi.carcompany.models;

import jakarta.persistence.*;


@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "purchasePrice", nullable = false, updatable = false)
    private Double purchasePrice;
    @Column(name = "sellingPrice")
    private Double sellingPrice;


    public Part() {
    }

    public Part(String name, String description, String location, int stock, Double purchasePrice, Double sellingPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.stock = stock;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }


    public Long getId() {
        return this.id;
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
}
