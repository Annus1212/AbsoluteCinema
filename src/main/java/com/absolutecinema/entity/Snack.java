package com.absolutecinema.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "snack")
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "images", columnDefinition = "BYTEA")
    @JsonIgnore
    private byte[] images;

    @Column(name = "snackssold", nullable = false)
    private int snackssold;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @JsonProperty("images")
    public String getImagesBase64() {
        if (images == null) return null;
        return java.util.Base64.getEncoder().encodeToString(images);
    }

    @JsonProperty("images")
    public void setImagesBase64(String base64) {
        if (base64 == null) {
            this.images = null;
            return;
        }
        this.images = java.util.Base64.getDecoder().decode(base64);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public int getSnackssold() {
        return snackssold;
    }

    public void setSnackssold(int snackssold) {
        this.snackssold = snackssold;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}