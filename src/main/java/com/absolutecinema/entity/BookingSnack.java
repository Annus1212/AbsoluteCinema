package com.absolutecinema.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking_snack")
@Data
public class BookingSnack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookingid", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "snackid", nullable = false)
    private Snack snack;
}