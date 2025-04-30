package com.absolutecinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookingsnacks")
public class BookingSnack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookingid", nullable = false)
    private Long bookingId;

    @Column(name = "snackid", nullable = false)
    private Long snackId;

    @Column(nullable = false)
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getSnackId() {
        return snackId;
    }

    public void setSnackId(Long snackId) {
        this.snackId = snackId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
} 